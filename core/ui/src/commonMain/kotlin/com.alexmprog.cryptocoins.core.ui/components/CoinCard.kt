package com.alexmprog.cryptocoins.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.alexmprog.common.utils.format.toFormattedString
import com.alexmprog.cryptocoins.core.ui.Res
import com.alexmprog.cryptocoins.core.ui.ic_downward_24
import com.alexmprog.cryptocoins.core.ui.ic_upward_24
import org.jetbrains.compose.resources.painterResource
import kotlin.math.abs

@Composable
fun CoinCard(
    modifier: Modifier,
    name: String,
    symbol: String,
    imageUrl: String,
    price: Double,
    priceChangePercentage24h: Double,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClick() },
        shape = MaterialTheme.shapes.large
    ) {
        CoinContent(name, symbol, imageUrl, price, priceChangePercentage24h)
    }
}

@Composable
fun CoinContent(
    name: String,
    symbol: String,
    imageUrl: String,
    price: Double,
    priceChangePercentage24h: Double,
) {
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = symbol.uppercase(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
        }
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${price.toFormattedString()}$",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val isPositiveNumber = priceChangePercentage24h >= 0
                val color = if (isPositiveNumber) Color.Green else Color.Red
                Icon(
                    modifier = Modifier.width(10.dp).height(6.dp),
                    painter = painterResource(
                        if (isPositiveNumber) Res.drawable.ic_upward_24 else Res.drawable.ic_downward_24
                    ),
                    contentDescription = null,
                    tint = color,
                )
                Text(
                    text = "${abs(priceChangePercentage24h).toFormattedString()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = color,
                )
            }
        }
    }
}