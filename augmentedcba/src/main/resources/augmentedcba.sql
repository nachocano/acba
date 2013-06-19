DROP TABLE IF EXISTS poi_action;
DROP TABLE IF EXISTS poi;

CREATE TABLE poi (
  poi_id 		MEDIUMINT UNSIGNED		NOT NULL AUTO_INCREMENT,
  attribution 	VARCHAR(150) 			DEFAULT NULL,
  title         VARCHAR(150) 			NOT NULL,
  latitude 		DECIMAL(20,10) 			NOT NULL,
  longitude 	DECIMAL(20,10) 			NOT NULL,
  image_url 	VARCHAR(255) 			DEFAULT NULL,
  line2 		VARCHAR(150) 			DEFAULT NULL,
  line3 		VARCHAR(150) 			DEFAULT NULL,
  line4 		VARCHAR(150) 			DEFAULT NULL,
  type 			SMALLINT UNSIGNED		DEFAULT 0,
  PRIMARY KEY  (poi_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;


CREATE TABLE poi_action (
  poi_action_id	MEDIUMINT UNSIGNED		NOT NULL AUTO_INCREMENT,
  poi_id  		MEDIUMINT UNSIGNED		NOT NULL DEFAULT 0,
  label 		VARCHAR(30) 			NOT NULL,
  uri 			VARCHAR(255) 			NOT NULL,
  content_type 		 VARCHAR(255) 		DEFAULT 'application/vnd.layar.internal',
  method 			 enum('GET','POST') DEFAULT 'GET',
  activity_type 	 SMALLINT 			DEFAULT NULL,
  PRIMARY KEY  (poi_action_id),
  CONSTRAINT fk_poi_action_poi_id		FOREIGN KEY (poi_id)	REFERENCES poi(poi_id)	ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;


INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (100,'Fundada en 1953', 'Universidad Tecnologica Nacional FRC', '-31.442541', '-64.193952', 'http://augmentedcba.com/data/utn/image_url.jpg', 'Maestro Marcelo L�pez','esq. Cruz Roja Argentina', 'Ciudad Universitaria', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (100, 'Mas Info', 'http://www.frc.utn.edu.ar/', 'text/html', 'GET', 1)
	  ,(100, 'Llamar UTN', 'tel:+543515986000', 'application/vnd.layar.internal', 'GET', 4)
	  ,(100, 'Mail UTN', 'mailto:secacad@sa.frc.utn.edu.ar', 'application/vnd.layar.internal','GET', 5)
	  ,(100, 'Info Posgrado Sistemas', 'http://www.posgrados.frc.utn.edu.ar/maestriaISI/?pIs=442', 'text/html', 'GET', 1);

INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (101, 'Estilo barroco', 'Iglesia de Nuestra Se�ora de la Asunci�n', '-31.416816', '-64.184725', 'http://augmentedcba.com/data/catedral/image_url.jpg', 'Catedral','Monumento Historico', 'Finalizada en 1706', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (101, 'Mas Informacion', 'http://es.wikipedia.org/wiki/Catedral_de_C%C3%B3rdoba_(Argentina)', 'text/html', 'GET', 1);

INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (102, 'Ex asilo y carcel de mujeres', 'Paseo del Buen Pastor', '-31.423129', '-64.187654', 'http://augmentedcba.com/data/paseo_buenpastor/image_url.jpg', 'Avda. Yrigoyen 325','Complejo Cultural', 'Inaugurado en 2007', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (102, 'Mas Informacion', 'http://www.cba.gov.ar/vercanal.jsp?idCanal=56339', 'text/html', 'GET', 1);

INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (103,'Fundado en 1687', 'Colegio Nacional de Monserrat', '-31.418851', '-64.187043', 'http://augmentedcba.com/data/monserrat/image_url.jpg', 'Patrimonio Cultural','de la Humanidad', 'por la Unesco', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (103, 'Mas Info', 'http://www.cnm.unc.edu.ar/', 'text/html', 'GET', 1)
	  ,(103, 'Llamar Monse', 'tel:+543514332079', 'application/vnd.layar.internal', 'GET', 4);

INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (104,'Inaugurado en 1995', 'Patio Olmos', '-31.419801', '-64.18789', 'http://augmentedcba.com/data/patio_olmos/image_url.jpg', 'Ex Colegio Olmos','Cerro en el 77', 'por da�os estructurales', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (104, 'Mas Info', 'http://www.patioolmos.com/', 'text/html', 'GET', 1)
	  ,(104, 'Locales', 'http://www.patioolmos.com/locales', 'text/html', 'GET', 1)
	  ,(104, 'Llamar', 'tel:+543515704199', 'application/vnd.layar.internal', 'GET', 4);
	  
INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (105,'Siglo XVII', 'Cabildo', '-31.416308', '-64.184508', 'http://augmentedcba.com/data/cabildo/image_url.jpg', 'Centro Cultural','Estilo', 'Colonial Espa�ol', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (105, 'Mas Info', 'http://es.wikipedia.org/wiki/Cabildo_de_C%C3%B3rdoba', 'text/html', 'GET', 1);

INSERT INTO poi (poi_id, attribution, title, latitude, longitude, image_url, line2, line3, line4, type)
VALUES (106,'Siglo XVII', 'Facultad de Derecho y Cs Sociales', '-31.418217', '-64.186689', 'http://augmentedcba.com/data/fac_abogacia/image_url.jpg', 'Mas Antigua','del Pais', '', 1);

INSERT INTO poi_action (poi_id, label, uri, content_type, method, activity_type)
VALUES (106, 'Mas Info', 'http://www.derecho.unc.edu.ar/', 'text/html', 'GET', 1);
