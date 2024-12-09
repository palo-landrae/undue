package com.palolandrae.undue.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palolandrae.undue.objects.Subscription
import com.palolandrae.undue.objects.SubscriptionDto
import com.palolandrae.undue.repositories.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository,
) : ViewModel() {

    private val _subscriptionList = MutableStateFlow<List<Subscription>?>(listOf())
    val subscriptionList: Flow<List<Subscription>?> = _subscriptionList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    private val _price = MutableStateFlow("")
    val price: Flow<String> = _price

    init {
        getSubscriptions()
    }

    fun getSubscriptions() {
        viewModelScope.launch {
            val subscriptions = subscriptionRepository.getSubscriptions()
            _subscriptionList.emit(subscriptions?.map { it -> it.asDomainModel() })
            Log.d("SubscriptionViewModel", "Fetched subscriptions: $subscriptions")
        }
    }

    fun removeItem(subscription: Subscription) {
        viewModelScope.launch {
            val newList = mutableListOf<Subscription>().apply { _subscriptionList.value?.let { addAll(it) } }
            newList.remove(subscription)
            _subscriptionList.emit(newList.toList())
            // Call api to remove
            subscriptionRepository.deleteSubscription(id = subscription.id)
            // Then fetch again
            getSubscriptions()
        }
    }

    fun addSubscription(subscription: Subscription) {
        viewModelScope.launch {
            val updatedList = mutableListOf<Subscription>().apply {
                _subscriptionList.value?.let { addAll(it) }
                add(subscription)
            }
            _subscriptionList.emit(updatedList.toList())
            subscriptionRepository.createSubscription(subscription)
            getSubscriptions()
            Log.d("SubscriptionViewModel", "Subscription added: $subscription")
        }
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onPriceChange(price: String) {
        _price.value = price
    }

    private fun SubscriptionDto.asDomainModel(): Subscription {
        return Subscription(
            id = this.id,
            name = this.name,
            price = this.price,
        )
    }

}