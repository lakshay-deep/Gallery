package com.example.gallery.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gallery.R
import com.example.gallery.data.Album
import com.example.gallery.ui.base.ShowError
import com.example.gallery.ui.base.ShowLoading
import com.example.gallery.ui.base.UIState
import com.example.gallery.ui.components.AlbumLayout
import com.example.gallery.ui.viewmodels.AlbumViewModel

@Composable
fun  AlbumScreen(
    albumViewModel: AlbumViewModel = hiltViewModel(),
    itemClicked: (Album) -> Unit
){
    val albumUiState: UIState<List<Album>> by albumViewModel.albumItem.collectAsStateWithLifecycle()

    when(albumUiState){
        is UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Failure -> {
            ShowError(
                text = stringResource(id = R.string.something_went_wrong),
                retryEnabled = true
            ){
                albumViewModel.fetchAlbumItems()
            }
        }

        is UIState.Success -> {
            AlbumLayout(albumList = (albumUiState as UIState.Success<List<Album>>).data){
                itemClicked(it)
            }
        }

        is UIState.Empty -> {

        }
    }

}