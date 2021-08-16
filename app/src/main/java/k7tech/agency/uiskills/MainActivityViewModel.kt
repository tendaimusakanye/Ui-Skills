package k7tech.agency.uiskills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _item = MutableLiveData<List<Item>>()
    val item: LiveData<List<Item>> = _item

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _pair = MutableLiveData<Pair<Boolean, String>>()
    val pair: LiveData<Pair<Boolean, String>> = _pair

    private val itemsSet = mutableSetOf<Item>()

    fun onItemClicked(item: Item) {
        viewModelScope.launch {
            if (!itemsSet.contains(item)) {
                itemsSet.add(item)
                _item.postValue(itemsSet.toList().sortedBy { it.title })
            }
        }
    }

    fun displayBottomSheet(title: String) {
        _title.postValue(title)
    }

    fun checkItemWithTitle(pair: Pair<Boolean, String>) {
        _pair.postValue(pair)
    }
}