
CREATE TABLE fault (
   	fault_id SERIAL PRIMARY KEY,
type_of_fault VARCHAR(50) NOT NULL,
   );

CREATE TABLE asset_fault(
	asset_failt_id SERIAL PRIMARY KEY,
	fault_id INT NULL,
	asset_id INT NOT NULL,
time_of_failure DATE NOT NULL,
   	fix_time DATE NULL,
	fixable BOOL
	FOREIGN KEY (fault_id) REFERENCES fault (fault_id)
	FOREIGN KEY (asset_id) REFERENCES asset (asset_id)
);

CREATE TABLE "location"(
   	location_id SERIAL PRIMARY KEY,
   	"state" VARCHAR(50) NOT NULL,
   	address VARCHAR(50) NOT NULL,
   	postcode INT NOT NULL
   );

CREATE TABLE department (
   	department_id SERIAL PRIMARY KEY,
   	department_name VARCHAR(50) NOT NULL,
  	department_location INT NOT NULL,
  	FOREIGN KEY (department_location) REFERENCES location (location_id)
   );

CREATE TABLE asset (
   	asset_id SERIAL PRIMARY KEY,
  	name VARCHAR(50) NOT NULL,
   	type VARCHAR(50) NOT NULL,
	qr_code VARCHAR(50) NULL,
   	asset_category VARCHAR(50)  NOT NULL,
   	asset_department INT  NOT NULL,
   	status VARCHAR(15) NOT NULL DEFAULT 'FREE',
   	FOREIGN KEY (asset_department) REFERENCES department (department_id)
   );

CREATE TABLE "user"(
   	user_id SERIAL PRIMARY KEY,
   	first_name VARCHAR(50) NOT NULL,
   	surname VARCHAR(50) NOT NULL,
  	city VARCHAR(50) NOT NULL,
   	address VARCHAR(50) NOT NULL,
   	postcode INT NOT NULL,
   	user_department INT NULL,
	login VARCHAR(50) NOT NULL,
	"password" VARCHAR(50) NOT NULL,
	is_admin BOOL NOT NULL,
   	FOREIGN KEY (user_department) REFERENCES department (department_id)
   );

CREATE TABLE tag (
   	tag_name VARCHAR(50) PRIMARY KEY
  );


CREATE TABLE asset_tag (
   	asset_tag_id SERIAL PRIMARY KEY,
   	used_tag VARCHAR(50) NOT NULL,
   	used_asset INT NOT NULL,
   	FOREIGN KEY (used_tag) REFERENCES tag (tag_name),
   	FOREIGN KEY (used_asset) REFERENCES asset (asset_id)
   );

CREATE TABLE ticket (
   	invoice_id SERIAL PRIMARY KEY,
   	time_created DATE NOT NULL,
	time_accepted DATE NULL,
   	time_assigned DATE NULL,
	time_returned DATE NULL,
   	user_info INT NULL,
   	asset_info INT NOT NULL,
	comment VARCHAR(200) NULL,
   	FOREIGN KEY (user_info) REFERENCES "user" (user_id),
   	FOREIGN KEY (asset_info) REFERENCES asset (asset_id)
   );

CREATE TABLE log (
   	log_id SERIAL PRIMARY KEY,
   	user_info INT NOT NULL,
	date DATE NOT NULL,
	instruction VARCHAR(70) NOT NULL,
   	FOREIGN KEY (user_info) REFERENCES "user" (user_id)
   );



faults
INSERT INTO fault(type_of_fault)
VALUES ('broken screen'),
('water damage'),
('charging connector broken'),
('usb not working'),
('mechanical damage')



tags
	INSERT INTO tag (tag_name)
	VALUES('red'),
		('green'),
		('black'),
		('yellow'),
		('pink'),
		('purple'),
		('white'),
		('golden'),
		('new'),
		('refurbished'),
		('rainbow'),
		



