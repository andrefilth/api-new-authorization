db.getCollection("users_v2").createIndex({ "consumerId": 1 }, {
    "unique": true
})