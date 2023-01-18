package com.selsela.takeefapp.ui.auth

import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

class MainUiState {
    var onSuccess: StateEvent = consumed
    var onLoading: StateEvent = consumed
    var onFailure: StateEventWithContent<String> = consumed()
}
