package com.application.yes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import android.net.Uri
import coil.compose.AsyncImage
import com.application.yes.Product
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManagementScreen(
    shopName: String,
    products: List<Product>,
    onBack: () -> Unit,
    onAddProduct: (Product) -> Unit,
    onCloseShop: () -> Unit
) {
    var isModalOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Products", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Catalog items for $shopName", fontSize = 10.sp, color = Color(0xFF64748B))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = { isModalOpen = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEF2FF), contentColor = Color(0xFF4F46E5)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Product", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
            ) {
                items(products.filter { it.shopId == "vendor_custom_shop" || it.shopId == "shop_3" }) { product ->
                    ProductItem(product)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedButton(
                        onClick = onCloseShop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444))
                    ) {
                        Text("Close My Shop", fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (isModalOpen) {
                AddProductModal(
                    onDismiss = { isModalOpen = false },
                    onSave = { name, price, quality, desc, stock, image ->
                        val newProduct = Product(
                            id = "custom_${System.currentTimeMillis()}",
                            name = name,
                            description = desc,
                            price = price.toIntOrNull() ?: 0,
                            image = if (image.isNotEmpty()) image else "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&q=80&w=300",
                            category = "General",
                            `class` = quality,
                            shopId = "vendor_custom_shop",
                            availability = "Immediate Stock",
                            stock = stock.toIntOrNull() ?: 0
                        )
                        onAddProduct(newProduct)
                        isModalOpen = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Surface(
                        color = when(product.`class`) {
                            "A" -> Color(0xFFFFF7ED)
                            "B" -> Color(0xFFEEF2FF)
                            else -> Color(0xFFECFDF5)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "Class ${product.`class`}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = when(product.`class`) {
                                "A" -> Color(0xFFC2410C)
                                "B" -> Color(0xFF4338CA)
                                else -> Color(0xFF047857)
                            }
                        )
                    }
                }
                Text(product.description, fontSize = 10.sp, color = Color(0xFF64748B), maxLines = 2)
                Row(modifier = Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${product.price} TSh", fontWeight = FontWeight.Bold, color = Color(0xFF4F46E5), fontSize = 12.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Stock: ${product.stock}", fontSize = 10.sp, color = Color(0xFF94A3B8))
                }
            }
        }
    }
}

@Composable
fun AddProductModal(onDismiss: () -> Unit, onSave: (String, String, String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quality by remember { mutableStateOf("B") }
    var desc by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("") }
    var tempUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                productImage = tempUri.toString()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = File(File(context.cacheDir, "product_images").apply { if (!exists()) mkdirs() }, "product_${System.currentTimeMillis()}.jpg")
            val uri = FileProvider.getUriForFile(context, "com.application.yes.fileprovider", file)
            tempUri = uri
            cameraLauncher.launch(uri)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onSave(name, price, quality, desc, stock, productImage) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("New Product") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF1F5F9))
                        .clickable {
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (productImage.isEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color(0xFF64748B))
                            Text("Take Product Photo", fontSize = 10.sp, color = Color(0xFF64748B))
                        }
                    } else {
                        AsyncImage(
                            model = productImage,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price (TSh)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock Quantity") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                
                Text("Quality Class: ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    qualityOptions().forEach { q ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { quality = q }) {
                            RadioButton(selected = quality == q, onClick = { quality = q })
                            Text(q, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    )
}

fun qualityOptions() = listOf("A", "B", "C")

@Preview(showBackground = true)
@Composable
fun ProductManagementScreenPreview() {
    MaterialTheme {
        ProductManagementScreen(
            shopName = "Kibo Stall",
            products = emptyList(),
            onBack = {},
            onAddProduct = {},
            onCloseShop = {}
        )
    }
}
