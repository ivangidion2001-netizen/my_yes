package com.application.yes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.application.yes.Product
import com.application.yes.Shop
import com.application.yes.initialProducts
import com.application.yes.initialShops

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopsListScreen(
    searchQuery: String,
    budget: String,
    selectedClass: String?,
    shops: List<Shop>,
    products: List<Product>,
    onBack: () -> Unit,
    onShopSelect: (String) -> Unit
) {
    val filteredShops = shops.filter { shop ->
        products.any { p -> 
            p.shopId == shop.id && 
            (selectedClass == null || p.`class` == selectedClass) &&
            (p.name.contains(searchQuery, ignoreCase = true) || p.description.contains(searchQuery, ignoreCase = true))
        }
    }.ifEmpty { shops.take(3) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Retail Stores", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Text(
                "Fair Price Filter Active",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F46E5)
            )
            Text(
                "Shops Having Your Product",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = if (budget.isNotEmpty())
                    "Showing stores stocking \"$searchQuery\" in Class ${selectedClass ?: "B"} within your $budget TSh budget"
                else
                    "Showing stores stocking \"$searchQuery\" in Class ${selectedClass ?: "B"}",
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredShops) { shop ->
                    val product = products.find { it.shopId == shop.id && it.`class` == selectedClass } 
                        ?: products.find { it.shopId == shop.id }
                        ?: products[0]

                    ShopCard(shop = shop, product = product, onClick = { onShopSelect(shop.id) })
                }
            }
        }
    }
}

@Composable
fun ShopCard(shop: Shop, product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(12.dp)) {
                AsyncImage(
                    model = shop.image,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(shop.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Surface(color = Color(0xFFFFF7ED), shape = RoundedCornerShape(4.dp)) {
                            Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(10.dp))
                                Text(shop.rating.toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                            }
                        }
                    }
                    Text(shop.description, fontSize = 10.sp, color = Color(0xFF64748B), maxLines = 1)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(12.dp))
                        Text(shop.location, fontSize = 10.sp, color = Color(0xFF94A3B8), maxLines = 1)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fair Price: ${product.price} TSh",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4F46E5)
                )
                Text(
                    text = product.availability,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF047857)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopsListScreenPreview() {
    MaterialTheme {
        ShopsListScreen(
            searchQuery = "Charger",
            budget = "20000",
            selectedClass = "B",
            shops = initialShops,
            products = initialProducts,
            onBack = {},
            onShopSelect = {}
        )
    }
}
