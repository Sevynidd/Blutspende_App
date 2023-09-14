package agb.loehne.blutspende_app.viewmodel.settings

import agb.loehne.blutspende_app.R
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DarstellungViewModel : ViewModel() {
    // Variablen
    private var _showDialog = mutableStateOf(false)
    private val _radioOptions = listOf("System", "Hell", "Dunkel")
    private val _imageIds =
        listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)

    // Getter
    val getShowDialog: Boolean get() = _showDialog.value
    val getRadioOptions: List<String> get() = _radioOptions
    val getImageIds: List<Int> get() = _imageIds

    // Setter
    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }


}