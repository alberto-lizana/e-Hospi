USE egesven;

INSERT INTO role(id_role, name_role) values (1, 'Administrador');
INSERT INTO role(id_role, name_role) values (2, 'Recepcionista');
INSERT INTO role(id_role, name_role) values (3, 'Médico');

SELECT * FROM role;

INSERT INTO sex(id_sex, name_sex) values (1, 'Masculino');
INSERT INTO sex(id_sex, name_sex) values (2, 'Femenino');

SELECT * FROM sex;

INSERT INTO user  (id_user,email_user, first_name_user, last_name_user1, last_name_user2, password_user, phone_user, run_user, id_role, id_sex)
			values(1, 'admin@gmail.com', 'Alberto', 'Lizana', 'Rojas', '123456789', '956967028', '19083370-3', 1, 1 );
            
SELECT * FROM user;


/* Reset */
DROP TABLE user;
DROP TABLE sex;
DROP TABLE role;
