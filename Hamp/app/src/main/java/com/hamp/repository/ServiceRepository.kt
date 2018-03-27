package com.hamp.repository

import com.hamp.R
import com.hamp.domain.Service

class ServiceRepository {

    fun loadServices(): List<Service> {
        val serviceList = mutableListOf<Service>()

        serviceList.add(Service("1", "Bolsa pequeña", R.drawable.five_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("2", "Bolsa grande", R.drawable.ten_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("3", "Funda de sofá", R.drawable.sofa_cover, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("4", "Mantas", R.drawable.blankets, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("5", "Cortinas", R.drawable.curtains, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("6", "Cojines", R.drawable.cushions, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("7", "Edredón grande", R.drawable.duvet, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("8", "Edredón pequeño", R.drawable.quilt, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))

        return serviceList
    }
}