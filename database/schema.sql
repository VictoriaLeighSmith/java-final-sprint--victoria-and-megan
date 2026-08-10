CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	username TEXT UNIQUE NOT NULL,
	password TEXT NOT NULL,
	email TEXT UNIQUE NOT NULL,
	phone_number TEXT NOT NULL,
	address TEXT NOT NULL,
	role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS workout_classes(
    class_id SERIAL PRIMARY KEY,
    trainer_id INT NOT NULL REFERENCES users(id),
    class_name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    class_date DATE NOT NULL,
    class_time TIME NOT NULL
);