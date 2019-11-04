package com.example.mangavinek.feature.detail.presentation.view.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.data.entity.favorite.FavoriteDB
import com.example.mangavinek.feature.detail.model.domain.DetailDomain
import com.example.mangavinek.feature.detail.model.domain.StatusChapterDomain
import com.example.mangavinek.feature.detail.presentation.view.adapter.StatusChapterAdapter
import com.example.mangavinek.feature.detail.presentation.view.fragment.DetailChapterFragment
import com.example.mangavinek.feature.detail.presentation.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_detail.*
import kotlinx.android.synthetic.main.layout_detail.text_title
import kotlinx.android.synthetic.main.layout_screen_error.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(), AnkoLogger {

    private val viewModel by viewModel<DetailViewModel>()

    private var title: String = ""
    private var imageUrl: String = ""
    private var url: String = ""
    private var favoriteDB: FavoriteDB? = null
    private var menu: Menu? = null
    private var menuItemFavorite: MenuItem? = null
    var listStatusChapterDomain = arrayListOf<StatusChapterDomain>()

    val adapterStatusChapter by lazy {
        StatusChapterAdapter(listStatusChapterDomain)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()
    }

    private fun loadData() {
        url = intent.getStringExtra("url")
        viewModel.fetchDetail(url)
        viewModel.getDetail.observeResource(this,
            onSuccess = {
                populateDetail(it)
                showSuccess()
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading(it)
            })

        viewModel.getListDetailStatusChapter.observe(this, Observer {
            populateListStatusChapter(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItemFavorite = menu.findItem(R.id.item_favorite)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.item_favorite -> {
                favoriteDB?.let { favorite ->
                    if (checkComicDataBase()) {
                        viewModel.removeComic(title)
                    } else {
                        viewModel.insertComic(favorite)
                    }
                    addIconCheckAndUncheckedComic()
                    toast(getString(if (checkComicDataBase()) R.string.action_button_favorite
                    else R.string.action_button_removed))
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeIconButtonFavorite(@DrawableRes iconDrawable: Int) {
        menuItemFavorite?.setIcon(iconDrawable)
    }

    private fun checkComicDataBase(): Boolean {
        return viewModel.searchTitle(title) > 0
    }

    private fun showMenuFavorite() {
        menuItemFavorite?.isVisible = true
    }

    private fun addIconCheckAndUncheckedComic() {
        showMenuFavorite()

        changeIconButtonFavorite(
            if (checkComicDataBase()) R.drawable.ic_favorite_24dp
            else R.drawable.ic_favorite_border_24dp
        )
    }

    private fun populateDetail(detailDomain: DetailDomain?) {
        detailDomain?.let {
            text_title.text = it.title
            text_title_original.text = it.titleOriginal.getStringBold(R.string.info_title_original)

            text_status.text = it.status
            text_publishing.text = it.publishing.getStringBold(R.string.info_publishing)
            text_publication.text = it.publication.getStringBold(R.string.info_publication)
            text_year.text = it.year.getStringBold(R.string.info_year)
            text_sinopse.text = it.sinopse
            val urlImage = BASE_URL.plus(it.imageCover)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover_complet)

            initRecycler()
            initFragment(it.link)
            insertInfoDatabase(it.title, urlImage)

            addIconCheckAndUncheckedComic()
        }
    }

    private fun insertInfoDatabase(title: String, urlImage: String) {
        this.title = title
        this.imageUrl = urlImage

        this.favoriteDB = FavoriteDB(
            title = title,
            image = urlImage,
            link = url
        )
    }

    private fun showSuccess() {
        layout_detail.visibility = View.VISIBLE
        include_layout_error.visibility = View.GONE
    }

    private fun showLoading(isVisible: Boolean) {
        include_layout_loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        layout_detail.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            viewModel.fetchDetail(url)
        }
    }

    private fun populateListStatusChapter(listStatusChapterDomain: MutableList<StatusChapterDomain>){
        this.listStatusChapterDomain.addAll(listStatusChapterDomain)
        adapterStatusChapter.notifyDataSetChanged()
    }

    private fun initRecycler() {
        with(recycler_chapter_status) {
            adapter = adapterStatusChapter
            isNestedScrollingEnabled = false
            isFocusable = false
            val gridLayoutManager = GridLayoutManager(this@DetailActivity, 4)
            layoutManager = gridLayoutManager
        }
    }

    private fun String.getStringBold(@StringRes textSpecific: Int): SpannableString {
        val spannableString = SpannableString(getString(textSpecific, this))
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            getString(textSpecific).replace(" %s", "").length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    private fun initFragment(link: String) {
        val fragment = DetailChapterFragment()
        val args = Bundle()
        args.putString("urlChapter", link)
        fragment.arguments = args

        supportFragmentManager.inTransaction {
            add(R.id.frame_chapter, fragment)

        }
        buttonShowChapter.setOnClickListener {
            checkButton(checked = buttonShowChapter, unChecked = buttonShowStatus, isCheck = true)
        }

        buttonShowStatus.setOnClickListener {
            checkButton(checked = buttonShowStatus, unChecked = buttonShowChapter, isCheck = false)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    @SuppressLint("PrivateResource")
    private fun checkButton(checked: Button, unChecked: Button, isCheck: Boolean) {
        checked.setBackgroundColor(ContextCompat.getColor(this, R.color.background_material_light))
        checked.setTextColor(ContextCompat.getColor(this, R.color.background_grey))

        unChecked.setBackgroundColor(ContextCompat.getColor(this, R.color.background_grey))
        unChecked.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))

        frame_chapter.visibility = if (isCheck) View.VISIBLE else View.GONE
        recycler_chapter_status.visibility = if (isCheck) View.GONE else View.VISIBLE
    }
}