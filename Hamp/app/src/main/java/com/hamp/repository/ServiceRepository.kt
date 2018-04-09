package com.hamp.repository

import com.hamp.R
import com.hamp.db.dao.ServiceQuantityDao
import com.hamp.db.domain.HampService
import com.hamp.db.domain.ServiceQuantity
import com.hamp.domain.Service
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
        private val serviceQuantityDao: ServiceQuantityDao
) {

    fun loadServices(): Single<List<Service>> {
        val serviceList = mutableListOf<HampService>()

        serviceList.add(HampService("1", "Bolsa pequeña", R.drawable.five_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("2", "Bolsa grande", R.drawable.ten_bag, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("3", "Funda de sofá", R.drawable.sofa_cover, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("4", "Mantas", R.drawable.blankets, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("5", "Cortinas", R.drawable.curtains, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("6", "Cojines", R.drawable.cushions, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("7", "Edredón grande", R.drawable.duvet, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))
        serviceList.add(HampService("8", "Edredón pequeño", R.drawable.quilt, "The quick brown fox jumpsover the lazy dog. LoremIpsum, The quick brown fox jumps over the lazy dog.Lorem Ipsum.", 0f))

        return Flowable.fromIterable(serviceList)
                .flatMapCompletable {
                    val completableList = arrayListOf(
                            saveHampServices(it),
                            saveServiceQuantity(it)
                    )
                    Completable.concat(completableList)
                }.andThen(serviceQuantityDao.getServicesQuantityDao())
    }

    private fun saveHampServices(hampService: HampService): Completable {
        return Completable.create {
            val service = serviceQuantityDao.getHampService(hampService.id)

            if (service != null) serviceQuantityDao.updateHampService(hampService)
            else serviceQuantityDao.saveHampService(hampService)

            it.onComplete()
        }
    }

    private fun saveServiceQuantity(hampService: HampService): Completable {
        return Completable.create {
            val service = serviceQuantityDao.getServiceQuantity(hampService.id)

            if (service != null) serviceQuantityDao.updateServiceQuantity(service)
            else serviceQuantityDao.saveServiceQuantity(ServiceQuantity(hampService.id, 0))

            it.onComplete()
        }
    }

    fun getServices() = serviceQuantityDao.getServicesQuantityDao()

    fun saveServiceQuantity(service: Service) {
        serviceQuantityDao.updateServiceQuantity(ServiceQuantity(service.hampService.id, service.quantity))
    }

    fun deleteBasket() {
        Completable.fromCallable { serviceQuantityDao.deleteAll() }.subscribeOn(Schedulers.io())
    }
}