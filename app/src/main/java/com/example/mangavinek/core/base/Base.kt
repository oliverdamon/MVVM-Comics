package com.example.mangavinek.core.base

interface Base<P, VM> {
    fun presenter(): P
    fun viewModel(): VM
}