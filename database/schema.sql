CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	username TEXT UNIQUE NOT NULL,
	password TEXT NOT NULL,
	email TEXT UNIQUE NOT NULL,
	phone_number TEXT NOT NULL,
	address TEXT NOT NULL,
	role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS workout_classes (
    class_id SERIAL PRIMARY KEY,
    trainer_id INT NOT NULL REFERENCES users(id),
    class_name TEXT NOT NULL,
    description TEXT NOT NULL,
    class_date DATE NOT NULL,
    class_time TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS memberships (
    membership_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    membership_type TEXT NOT NULL,
    price DECIMAL NOT NULL,
    purchase_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS merchandise (
    merchandise_id SERIAL PRIMARY KEY,
    product_name TEXT NOT NULL,
    type TEXT NOT NULL,
    price DECIMAL NOT NULL,
    stock_level INT NOT NULL
);