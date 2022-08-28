import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.karetao.data.use_case.FlashCardOrderType
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.presentation.flashcards.components.DefaultRadioButton

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    flashCardOrderType: FlashCardOrderType = FlashCardOrderType.Question(OrderType.Ascending),
    onOrderChange: (FlashCardOrderType) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Question",
                selected = flashCardOrderType is FlashCardOrderType.Question,
                onSelect = { onOrderChange(FlashCardOrderType.Question(flashCardOrderType.orderType))}
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Answer",
                selected = flashCardOrderType is FlashCardOrderType.Answer,
                onSelect = { onOrderChange(FlashCardOrderType.Answer(flashCardOrderType.orderType))}
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Ascending",
                selected = flashCardOrderType.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(flashCardOrderType.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = flashCardOrderType.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(flashCardOrderType.copy(OrderType.Descending))
                }
            )
        }
    }
}