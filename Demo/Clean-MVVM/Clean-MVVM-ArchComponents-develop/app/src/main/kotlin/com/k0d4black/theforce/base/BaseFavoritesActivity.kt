/*
*
* Copyright 2020 David Odari
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except 
* in compliance with the License. You may obtain a copy of the License at
*
*          http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License 
* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing permissions and limitations under
* the License.
*
*/
package com.k0d4black.theforce.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.k0d4black.theforce.R
import com.k0d4black.theforce.activities.IFavoritesBinder
import com.k0d4black.theforce.commons.NavigationUtils
import com.k0d4black.theforce.commons.showSnackbar
import com.k0d4black.theforce.models.FavoritePresentation
import com.k0d4black.theforce.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal abstract class BaseFavoritesActivity : BaseActivity(), IFavoritesBinder {

    // region Members

    private var isFavorite = false

    private val favoritesViewModel by viewModel<FavoriteViewModel>()

    //Used to check if is favorite on init
    protected var characterName = ""
    protected var favoritePresentation: FavoritePresentation? = null

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val favorite =
            intent.getParcelableExtra<FavoritePresentation>(NavigationUtils.FAVORITE_PARCEL_KEY)

        favorite?.let { favoritePresentation ->
            bindFavorite(favoritePresentation)
            characterName = favoritePresentation.characterPresentation.name
            this.favoritePresentation = favoritePresentation
            checkIfFavorite()
            invalidateOptionsMenu()
        }

        observeFavoriteViewState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.getItem(0)
        if (isFavorite)
            menuItem?.setIcon(R.drawable.ic_favs_24dp)
        else
            menuItem?.setIcon(R.drawable.ic_no_favs_24dp)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_alter_favorites -> {
                if (isFavorite) {
                    removeFromFavorites()
                    isFavorite = !isFavorite
                } else {
                    favoritePresentation?.let { favorite ->
                        addToFavorites(favorite)
                        isFavorite = !isFavorite
                    }
                }
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

    // region Public API

    fun checkIfFavorite() {
        favoritesViewModel.getFavorite(characterName)
    }

    // endregion

    // region Private API

    private fun addToFavorites(favorite: FavoritePresentation) {
        favoritesViewModel.saveFavorite(favorite)
        showSnackbar(
            rootViewGroup,
            getString(R.string.info_added_to_favs)
        )
    }

    private fun removeFromFavorites() {
        favoritesViewModel.deleteFavorite(characterName)
        showSnackbar(
            rootViewGroup,
            getString(R.string.info_removed_from_favs)
        )
    }

    private fun observeFavoriteViewState() {
        favoritesViewModel.favoriteViewState.observe(this, Observer {
            isFavorite = it.isFavorite
            invalidateOptionsMenu()
            it.error?.let { e ->
                showSnackbar(rootViewGroup, getString(e.message))
            }
        })
    }

    // endregion

    // region Abstract

    abstract val rootViewGroup: ViewGroup

    // endregion
}