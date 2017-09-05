package com.hamp.repository

import com.hamp.R
import com.hamp.domain.Service

class ServiceRepository {

    fun loadServices(): List<Service> {
        val serviceList = mutableListOf<Service>()

        serviceList.add(Service("Bolsa pequeña", R.drawable.five_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Bolsa grande", R.drawable.ten_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Funda de sofá", R.drawable.sofa_cover, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Mantas", R.drawable.blankets, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Cortinas", R.drawable.curtains, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Cojines", R.drawable.cushions, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Edredón grande", R.drawable.duvet, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(Service("Edredón pequeño", R.drawable.quilt, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))

        return serviceList
    }
}