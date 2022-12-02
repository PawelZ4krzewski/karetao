import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.cardGroup.CardGroupOrderType
import com.example.karetao.presentation.flashcards.components.DefaultRadioButton

@Composable
fun OrderSectionCardGroup(
    modifier: Modifier = Modifier,
    cardGroupOrderType: CardGroupOrderType = CardGroupOrderType.GroupName(OrderType.Ascending),
    onOrderChange: (CardGroupOrderType) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Ascending",

                selected = cardGroupOrderType.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(cardGroupOrderType.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = cardGroupOrderType.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(cardGroupOrderType.copy(OrderType.Descending))
                }
            )
        }
    }
}