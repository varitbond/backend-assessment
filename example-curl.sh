# USER
# Get an user GET /users/get/{id}
curl --location 'http://localhost:3000/users/get/1' \
--header 'Authorization: Bearer xxxxxxxxxxx'

# List all users GET /users/get
curl --location 'http://localhost:3000/users/get' \
--header 'Authorization: Bearer xxxxxxxxxxx' \

# Create an user POST /users/create
curl --location 'http://localhost:3000/users/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data-raw '{
    "name": "Jane Smith",
    "username": "janesmith",
    "email": "janesmith@example.com",
    "address": {
        "street": "456 Oak St",
        "suite": "Suite 202",
        "city": "Oakland",
        "zipcode": "54321",
        "geo": {
            "lat": "37.8044",
            "lng": "-122.2711"
        }
    },
    "phone": "987-654-3210",
    "website": "www.example.org",
    "company": {
        "name": "Another Company",
        "catchPhrase": "Creating new ideas",
        "bs": "Innovation services"
    }
}'

# Update an user PUT /users/update
curl --location --request PUT 'http://localhost:3000/users/update' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data-raw '{
    "id": 1,
    "name": "John Doe",
    "username": "johndoe",
    "email": "johndoe@example.com",
    "address": {
        "street": "123 Main St",
        "suite": "Apt 101",
        "city": "Springfield",
        "zipcode": "12345",
        "geo": {
            "lat": "40.7128",
            "lng": "-74.0060"
        }
    },
    "phone": "123-456-7890",
    "website": "www.example.com",
    "company": {
        "name": "Example Company",
        "catchPhrase": "Making examples since 2000",
        "bs": "Business services"
    }
}'

# Patch an user PATCH /users/patch
curl --location --request PATCH 'http://localhost:3000/users/patch' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data '{
    "id": 1,
    "address": {
        "geo": {
            "lat": "-37.3159000",
            "lng": "81.1496000"
        }
    }
}'

# Delete an user DELETE /users/delete
curl --location --request DELETE 'http://localhost:3000/users/delete/1' \
--header 'Authorization: Bearer xxxxxxxxxxx' \

# Filter an user GET /users/get/by-postid
curl --location 'http://localhost:3000/users/get/by-postid?postId=1' \
--header 'Authorization: Bearer xxxxxxxxxxx' \


# POST
# Get a post GET /posts/get/{id}
curl --location 'http://localhost:3000/posts/get/1' \
--header 'Authorization: Bearer xxxxxxxxxxx'

# List all posts GET /posts/get
curl --location 'http://localhost:3000/posts/get' \
--header 'Authorization: Bearer xxxxxxxxxxx' \

# Create a post POST /posts/create
curl --location 'http://localhost:3000/posts/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data '{
    "userId": 1,
    "title": "This is title",
    "body": "This is body"
}'

# Update a post PUT /posts/update
curl --location --request PUT 'http://localhost:3000/posts/update' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data '{
    "id": 1,
    "userId": 1,
    "title": "This is update title",
    "body": "This is update body"
}'

# Patch a post PATCH /posts/patch
curl --location --request PATCH 'http://localhost:3000/posts/patch' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
--data '{
    "id": 1,
    "title": "This is patch title"
}'

# Delete a posts DELETE /posts/delete
curl --location --request DELETE 'http://localhost:3000/posts/delete/1' \
--header 'Authorization: Bearer xxxxxxxxxxx' \

# Filter a post GET /posts/get/by-userid
curl --location 'http://localhost:3000/posts/get/by-userid?userId=1' \
--header 'Authorization: Bearer xxxxxxxxxxx' \
