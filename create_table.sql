USE training_point;

CREATE TABLE faculty (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE class (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role ENUM('ADMIN', 'STUDENT', 'ASSISTANT'),
    avatar VARCHAR(255),
    gender ENUM('Male', 'Female', 'Other'),
    dob DATE,
    is_active BOOLEAN DEFAULT TRUE,
    
    unique(username)
);

CREATE TABLE student (
    id INT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    class_id INT,
    faculty_id INT,
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (class_id) REFERENCES class(id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

CREATE TABLE assistant (
    id INT PRIMARY KEY,
    faculty_id INT,
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

CREATE TABLE pointgroup (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    content TEXT,
    max_point INT
);

CREATE TABLE activity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    pointgroup_id INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pointgroup_id) REFERENCES pointgroup(id)
);

CREATE TABLE mission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    activity_id INT,
    point INT,
    content TEXT,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (activity_id) REFERENCES activity(id)
);

CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT,
    image VARCHAR(255),
    activity_id INT,
    user_id INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (activity_id) REFERENCES activity(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE `Like` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    post_id INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE Comment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    post_id INT,
    content TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE registermission (
    student_id INT,
    mission_id INT,
    is_completed BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (mission_id) REFERENCES mission(id),
    PRIMARY KEY (student_id, mission_id)
);

CREATE TABLE missingreport (
    student_id INT,
    mission_id INT,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (mission_id) REFERENCES mission(id),
    PRIMARY KEY (student_id, mission_id)
);
