package com.example.mangavinek.core.helper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addPaginationScroll(layoutManager: LinearLayoutManager,
                                  loadMoreItems: () -> Unit, getTotalPageCount: () -> Int = { 0 },
                                  isLoading: () -> Boolean,
                                  isLastPage: () -> Boolean = { false },
                                  hideOthersItems: () -> Unit){

    this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (isLoading() && !isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= getTotalPageCount()) {
                        loadMoreItems.invoke()
                    }
                }
                hideOthersItems.invoke()
            }
        }
    })
}