# --- First database schema

# --- !Ups

create table book (
  id                        bigint not null,
  title                     varchar(255),
  author                    varchar(255),
  constraint pk_book primary key (id))
;

create table chapter (
  id                        bigint not null,
  title                     varchar(255),
  chap_number               bigint,
  book_id                   bigint,
  constraint pk_chapter primary key (id))
;

create table page (
  id                        bigint not null,
  page_number                bigint,
  page_content               longtext,
  chapter_id                bigint,
  constraint pk_page primary key (id))
;

create sequence book_seq start with 10;

create sequence chapter_seq start with 10;

create sequence page_seq start with 10;

alter table chapter add constraint fk_chapter_book_1 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_chapter_book_1 on chapter (book_id);

alter table page add constraint fk_page_chapter_1 foreign key (chapter_id) references chapter (id) on delete restrict on update restrict;
create index ix_page_chapter_1 on page (chapter_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists chapter;

drop table if exists page;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

drop sequence if exists chapter_seq;

drop sequence if exists page_seq;

