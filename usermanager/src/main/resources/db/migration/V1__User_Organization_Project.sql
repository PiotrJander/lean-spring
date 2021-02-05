CREATE TABLE `user` (
  `id` char(36) CHARACTER SET utf8mb4,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);

CREATE TABLE `organization` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `organization_name_unique` (`name`)
);

CREATE TABLE `project` (
  `id` binary(16),
  `organization_id` binary(16) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_name_in_organization_unique` (`organization_id`,`name`),
  KEY `organization_id` (`organization_id`),
  FULLTEXT KEY `project_name_full_text_idx` (`name`),
  CONSTRAINT `project_fk_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
  ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE `user_in_organization` (
  `user_id` char(36) CHARACTER SET utf8mb4,
  `organization_id` binary(16),
  `role` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`organization_id`),
  KEY `organization` (`organization_id`),
  KEY `user` (`user_id`),
  CONSTRAINT `user_in_organization_fk_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
  ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_in_organization_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `user_in_project` (
  `project_id` binary(16),
  `user_id` char(36) CHARACTER SET utf8mb4,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `user_projects` (`user_id`),
  KEY `project_users` (`project_id`),
  CONSTRAINT `user_in_project_fk_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
  ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_in_project_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
  ON DELETE CASCADE ON UPDATE NO ACTION
);
