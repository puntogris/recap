package com.puntogris.recap.ui.recap.create

import com.puntogris.recap.R

sealed class RecapHeaderValidator{
    class NotValid(val error: Int): RecapHeaderValidator()
    object Valid: RecapHeaderValidator()

    companion object {

        fun from(title: String, season: Int?, episode: Int?): RecapHeaderValidator{
            var errorResId = 0

            if (title.isEmpty()) errorResId = R.string.snack_recap_title_required
            if (season == null)  errorResId = R.string.snack_recap_season_required
            if (episode == null) errorResId = R.string.snack_recap_episode_required

            return if (errorResId != 0) NotValid(errorResId) else Valid
        }
    }
}
