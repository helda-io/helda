(ns concierge-bot.storages.common.repository)

(defprotocol Repository
  (index [this fields unique])

  (find-one [this id] [this field value])
  (find-all [this where-fields sort-fields]
    "Please use array-map for sort-fields to save order")
  (find-distinct [this field] "Get distinct values for fields")

  (save [this rec])

  (delete-one [this id])
  (delete-all [this fields])
  )

(defn fk-index [repository fields unique]
  (index repository fields unique)
  repository
  )
