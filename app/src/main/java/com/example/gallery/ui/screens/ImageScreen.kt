package com.example.gallery.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gallery.R
import com.example.gallery.data.Image
import com.example.gallery.ui.base.ShowError
import com.example.gallery.ui.base.ShowLoading
import com.example.gallery.ui.base.UIState
import com.example.gallery.ui.components.ImageLayout
import com.example.gallery.ui.viewmodels.ImageViewModel

@Composable
fun ImageScreen(
    imageViewModel: ImageViewModel = hiltViewModel()
) {
    val imageUiState: UIState<Image> by imageViewModel.imageItem.collectAsStateWithLifecycle()

    when(imageUiState) {
        is UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Failure -> {
            ShowError(text = stringResource(id = R.string.something_went_wrong))
        }

        is UIState.Success -> {
            ImageLayout((imageUiState as UIState.Success<Image>).data)
        }

        is UIState.Empty -> {

        }
    }
}