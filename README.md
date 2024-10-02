
# Receipt Processor Web Service

This project is a web service built using Spring Boot that processes receipts and calculates points based on specific rules. 

The application runs inside a Docker container, with two options for running the service.

## API Endpoints

### 1. Process Receipts
- **Method**: `POST`
- **Path**: `/receipts/process`
- **Payload**: Receipt JSON (example below)
- **Response**: JSON object with a generated ID for the receipt.

Example payload:
```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },
    {
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    }
  ],
  "total": "18.74"
}
```

Example response:
```json
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }
```

### 2. Get Points
- **Method**: `GET`
- **Path**: `/receipts/{id}/points`
- **Response**: A JSON object containing the number of points awarded for the receipt.

Example response:
```json
{ "points": 35 }
```

## Running the Application

You have two options to run the application: using Docker Hub or loading the Docker image from a `.tar` file.

### Option 1: Run the Application from Docker Hub

1. **Ensure Docker is installed** on your system.
   - [Install Docker](https://docs.docker.com/get-docker/) if it's not already installed.

2. **Pull the Docker image** from Docker Hub:
   ```bash
   docker pull deepfuriya/receipt-processor
   ```

3. **Run the Docker container**:
   ```bash
   docker run -p 8080:8080 deepfuriya/receipt-processor
   ```

4. Access the application at `http://localhost:8080` in your browser or via API clients like Postman or `curl`.

---

### Option 2: Load the Docker Image from the `.tar` File

1. **Download the Docker image file** (`receipt-processor.tar`) from the GitHub repository.

2. **Load the Docker image** from the `.tar` file:
   ```bash
   docker load -i receipt-processor.tar
   ```

3. **Run the Docker container**:
   ```bash
   docker run -p 8080:8080 receipt-processor
   ```

4. Access the application at `http://localhost:8080` in your browser or via API clients like Postman or `curl`.

---



## Conclusion

You can choose either of the two options to run the application on your system:
1. Pull the Docker image from Docker Hub (`deepfuriya/receipt-processor`).
2. Load the Docker image from the `.tar` file (`receipt-processor.tar`).

For any questions or further instructions, please feel free to contact me.
