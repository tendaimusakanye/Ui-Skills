package k7tech.agency.uiskills.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k7tech.agency.uiskills.Item
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val _item = MutableLiveData<List<Item>>()
    val item: LiveData<List<Item>> = _item

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val itemsSet = mutableSetOf<Item>()

    fun onItemClicked(item: Item) {
        viewModelScope.launch {
            if (!itemsSet.contains(item)) {
                itemsSet.add(item)
                _item.postValue(itemsSet.toList().sortedBy { it.title })
            }
        }
    }

    fun checkItemWithTitle(title: String) {
        _title.postValue(title)
    }
}