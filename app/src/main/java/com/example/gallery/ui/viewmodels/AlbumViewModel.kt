package com.example.gallery.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.data.Album
import com.example.gallery.data.repository.GalleryRepository
import com.example.gallery.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
):  ViewModel(){

    private val _albumItem = MutableStateFlow<UIState<List<Album>>>(UIState.Empty)
    val albumItem: StateFlow<UIState<List<Album>>> = _albumItem


    init {
        fetchAlbumItems()
    }

    fun fetchAlbumItems(){
        viewModelScope.launch {
            _albumItem.emit(UIState.Loading)
            galleryRepository.getAlbumList()
                .flowOn(Dispatchers.IO)
                .catch {
                    _albumItem.emit(UIState.Failure(it))
                }
                .collect{
                    _albumItem.emit(UIState.Success(it))
                }
        }
    }
}