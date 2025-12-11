#!/bin/bash

# Test Script for Hexagonal Architecture Spring Boot 3 Example
# This script demonstrates all API endpoints

echo "=========================================="
echo "Testing Hexagonal Architecture API"
echo "=========================================="
echo ""

BASE_URL="http://localhost:8080/api/products"

echo "1. Creating a Laptop..."
LAPTOP_RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High performance laptop",
    "price": 999.99,
    "stock": 10
  }')
echo $LAPTOP_RESPONSE | jq .
LAPTOP_ID=$(echo $LAPTOP_RESPONSE | jq -r '.id')
echo ""

echo "2. Creating a Mouse..."
MOUSE_RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mouse",
    "description": "Wireless gaming mouse",
    "price": 49.99,
    "stock": 25
  }')
echo $MOUSE_RESPONSE | jq .
MOUSE_ID=$(echo $MOUSE_RESPONSE | jq -r '.id')
echo ""

echo "3. Getting all products..."
curl -s $BASE_URL | jq .
echo ""

echo "4. Getting Laptop by ID..."
curl -s $BASE_URL/$LAPTOP_ID | jq .
echo ""

echo "5. Decreasing Laptop stock by 3..."
curl -s -X POST "$BASE_URL/$LAPTOP_ID/decrease-stock?quantity=3" | jq .
echo ""

echo "6. Updating Laptop details..."
curl -s -X PUT $BASE_URL/$LAPTOP_ID \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High performance gaming laptop with RTX 4090",
    "price": 1299.99,
    "stock": 5
  }' | jq .
echo ""

echo "7. Getting all products after updates..."
curl -s $BASE_URL | jq .
echo ""

echo "8. Deleting Mouse..."
curl -s -X DELETE $BASE_URL/$MOUSE_ID
echo "Mouse deleted successfully"
echo ""

echo "9. Getting all products after deletion..."
curl -s $BASE_URL | jq .
echo ""

echo "=========================================="
echo "All tests completed successfully!"
echo "=========================================="
