package com.application.yes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionScreen(
    userType: String?,
    onUserTypeSelect: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Who are you?",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
            Text(
                text = "Identify your role to access specialized Kariakoo Market tools & fair-price indexing.",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            SelectionCard(
                title = "Customer",
                description = "Search for items standardized across Class A, B, C and find optimal stalls with fair prices.",
                icon = Icons.Default.Person,
                isSelected = userType == "customer",
                onClick = { onUserTypeSelect("customer") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCard(
                title = "Vendor / Merchant",
                description = "Register your physical stall credentials, catalog inventory grades, and advertise spot availability.",
                icon = Icons.Default.Store,
                isSelected = userType == "vendor",
                onClick = { onUserTypeSelect("vendor") }
            )
        }

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
            enabled = userType != null
        ) {
            Text("Send Identification", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SelectionCard(
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF6366F1) else Color(0xFFE2E8F0)
    val backgroundColor = if (isSelected) Color(0xFFEEF2FF) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(if (isSelected) Color(0xFF4F46E5) else Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else Color(0xFF64748B)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0F172A)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                lineHeight = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Selection Screen - No Selection")
@Composable
fun SelectionScreenNoSelectionPreview() {
    MaterialTheme {
        SelectionScreen(
            userType = null,
            onUserTypeSelect = {},
            onNext = {}
        )
    }
}

@Preview(showBackground = true, name = "Selection Screen - Customer Selected")
@Composable
fun SelectionScreenCustomerSelectedPreview() {
    MaterialTheme {
        SelectionScreen(
            userType = "customer",
            onUserTypeSelect = {},
            onNext = {}
        )
    }
}

@Preview(showBackground = true, name = "Selection Screen - Vendor Selected")
@Composable
fun SelectionScreenVendorSelectedPreview() {
    MaterialTheme {
        SelectionScreen(
            userType = "vendor",
            onUserTypeSelect = {},
            onNext = {}
        )
    }
}
