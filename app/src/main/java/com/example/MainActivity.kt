package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import com.example.ui.theme.MyApplicationTheme

data class CryptoAsset(val symbol: String, val name: String, val price: String, val change: String)

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          CryptoDashboard(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun CryptoDashboard(modifier: Modifier = Modifier) {
  val assets = listOf(
    CryptoAsset("BTC", "Bitcoin", "$64,231.50", "+1.45%"),
    CryptoAsset("ETH", "Ethereum", "$3,482.12", "-0.24%"),
    CryptoAsset("BNB", "BNB Chain", "$592.40", "+4.12%")
  )

  Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp)) {
    // Total Balance Section
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
      Column(modifier = Modifier.padding(24.dp)) {
        Text("Total Balance (USD)", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
        Text("$42,850.24", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimary)
        Text("+$1,240.12 (2.98%) Today", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)
      }
    }
    Spacer(modifier = Modifier.height(24.dp))
    
    // Quick Actions
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      QuickAction("⚡", "Convert")
      QuickAction("🎁", "Rewards")
      QuickAction("📈", "Staking")
      QuickAction("⋮", "More")
    }
    Spacer(modifier = Modifier.height(24.dp))

    // Markets Section
    Text("Markets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      items(assets) { asset ->
        CryptoItem(asset)
      }
    }
  }
}

@Composable
fun QuickAction(icon: String, name: String) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Surface(
      modifier = Modifier.size(48.dp),
      shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
      color = SurfaceDark
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(icon, style = MaterialTheme.typography.titleLarge)
      }
    }
    Text(name, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
  }
}

@Composable
fun CryptoItem(asset: CryptoAsset) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Simple icon indicator
        Surface(
          modifier = Modifier.size(40.dp),
          shape = androidx.compose.foundation.shape.CircleShape,
          color = if (asset.symbol == "BTC") BTCColor else if (asset.symbol == "ETH") ETHColor else BNBColor
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(asset.symbol.take(1), color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.Bold)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(asset.symbol, fontWeight = FontWeight.Medium)
          Text(asset.name, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
      }
      Column(horizontalAlignment = Alignment.End) {
        Text(asset.price, fontWeight = FontWeight.Medium)
        Text(
          asset.change,
          style = MaterialTheme.typography.bodySmall,
          color = if (asset.change.startsWith("+")) GreenGain else RedLoss
        )
      }
    }
  }
}
