#!/bin/bash

while IFS=, read -r Strategy TimeFrame Direction SecType OrderType Tif Quantity OrderId Side; do
    JSON_DATA="{\"strategy\":\"$Strategy\",\"symbol\":\"$Symbol\",\"timeFrame\":\"$TimeFrame\",\"direction\":\"$Direction\",\"secType\":\"$SecType\",\"orderType\":\"$OrderType",\"tif\":\"$Tif\",\"quantity\":\"$Quantity\",\"orderId\":\"$OrderId\",\"side\":\"$Side\",\"comment\":\"$Comment\",\"timeStamp\":\"$TimeStamp\",\"price\":\"$Price"}"											
    curl -X POST -H "Content-Type: application/json" -d "$JSON_DATA" http://localhost:8080/ibkrhub/signals/new
done < List_of_Alerts.csv