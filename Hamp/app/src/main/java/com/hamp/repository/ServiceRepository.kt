package com.hamp.repository

import com.hamp.R
import com.hamp.db.dao.ServiceQuantityDao
import com.hamp.db.domain.HampService
import com.hamp.db.domain.ServiceQuantity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
        private val serviceQuantityDao: ServiceQuantityDao
) {

    fun loadServices(): Completable {
        val serviceList = mutableListOf<HampService>()

        serviceList.add(HampService("1", "Bolsa pequeña", R.drawable.five_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("2", "Bolsa grande", R.drawable.ten_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("3", "Funda de sofá", R.drawable.sofa_cover, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("4", "Mantas", R.drawable.blankets, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("5", "Cortinas", R.drawable.curtains, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("6", "Cojines", R.drawable.cushions, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("7", "Edredón grande", R.drawable.duvet, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("8", "Edredón pequeño", R.drawable.quilt, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))

        return Completable.fromAction { serviceQuantityDao.saveServices(serviceList) }
                .concatWith { saveServiceQuantity(serviceList) }
    }

    private fun saveServiceQuantity(serviceList: MutableList<HampService>) {
        serviceQuantityDao.saveServicesQuantities(
                serviceList.flatMap { listOf(ServiceQuantity(it.id, 0)) })
    }

    fun getServices() = serviceQuantityDao.getServicesQuantityDao()

    fun deleteBasket() {
        Completable.fromCallable { serviceQuantityDao.deleteAll() }.subscribeOn(Schedulers.io())
    }
}