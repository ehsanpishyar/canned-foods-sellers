package ir.androad.repository.data_source

import ir.androad.cache.AppDatabase
import ir.androad.domain.models.Seller
import ir.androad.domain.models.responses.SellerResponse
import ir.androad.domain.repositories.SellerRepository
import ir.androad.domain.utils.ServiceResult
import ir.androad.network.ApiService
import ir.androad.network.models.responses.SellerResponseDto
import ir.androad.repository.mappers.toDomain
import ir.androad.repository.mappers.toDto
import ir.androad.repository.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SellerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    appDatabase: AppDatabase
): SellerRepository {

    private val sellerDao = appDatabase.sellerDao()

    override suspend fun insertSeller(seller: Seller): ServiceResult<SellerResponse> {
        val sellerEntity = seller.toEntity()
        val sellerDto = sellerEntity.toDto()

        val remoteSeller = try {
            apiService.insertSeller(sellerDto)
        } catch (e: IOException) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        } catch (e: Exception) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        }

        remoteSeller.let { sellerResponseDto ->
            sellerDao.insertSeller(sellerResponseDto.toEntity()).let { sellerResponseEntity ->
                return ServiceResult.Success(data = sellerResponseEntity.toDomain())
            }
        }
    }

    override fun getSellers(): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))

        val sellers = sellerDao.fetchSellers()?.map {
            it.toDomain()
        }
        emit(ServiceResult.Success(sellers))

        val remoteSellers = try {
            apiService.getSellers()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as SellerResponseDto

        remoteSellers.let { sellerResponseDto ->
            sellerDao.deleteSellers()
            sellerDao.insertSeller(sellerResponseDto.toEntity())
            emit(ServiceResult.Success(data = sellerDao.fetchSellers()?.map { it.toDomain() }))
        }
        emit(ServiceResult.Loading(isLoading = false))
    }

    override suspend fun getSellerById(id: Long): ServiceResult<SellerResponse> {
        return try {
            val remoteSeller = apiService.getSellerById(id)
            ServiceResult.Success(remoteSeller.toEntity().toDomain())
        } catch (e: IOException) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        } catch (e: Exception) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        }
    }

    override fun getSellersByTitle(title: String?): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByTitle(title)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersByDescription(description: String?): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByDescription(description)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersByLocationTitle(locationTitle: String?): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByLocationTitle(locationTitle)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersByResultTitle(resultTitle: String?): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByResultTitle(resultTitle)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersBySellerCategoryId(sellerCategoryId: Int): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersBySellerCategoryId(sellerCategoryId)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersByResultCategoryId(resultCategoryId: Int): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByResultCategoryId(resultCategoryId)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override fun getSellersByFoodCategoryId(foodCategoryId: Int): Flow<ServiceResult<List<SellerResponse>>> = flow {
        emit(ServiceResult.Loading(isLoading = true))
        val remoteSellers = try {
            apiService.getSellersByFoodCategoryId(foodCategoryId)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ServiceResult.Error(data = null, message = e.message))
        } as List<SellerResponseDto>

        emit(ServiceResult.Success(remoteSellers.map { it.toEntity().toDomain() }))
        emit(ServiceResult.Loading(isLoading = false))
    }

    override suspend fun updateSeller(seller: Seller): ServiceResult<Boolean> {
        val sellerEntity = seller.toEntity()
        val sellerDto = sellerEntity.toDto()

        val remoteSeller = try {
            apiService.updateSeller(sellerId = sellerDto.id!!, sellerDto = sellerDto)
        } catch (e: IOException) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        } catch (e: Exception) {
            e.printStackTrace()
            return ServiceResult.Error(data = null, message = e.message)
        }

        remoteSeller.let {
            sellerDao.deleteSellers()
            sellerDao.insertSeller(it.toEntity())
            return ServiceResult.Success(data = true)
        }
    }
}