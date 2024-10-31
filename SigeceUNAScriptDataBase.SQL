/*
Created: 8/8/2024
Modified: 19/10/2024
Model: Oracle 19c
Database: Oracle 19c
*/


-- Create sequences section -------------------------------------------------

CREATE SEQUENCE usr_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE cht_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE msg_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE cnst_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE ntp_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE eml_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE emm_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE var_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE syst_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE rol_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE fle_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE are_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE act_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE mgt_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE mgtA_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE tcg_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

CREATE SEQUENCE sAct_ID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 NOCACHE
;

-- Create tables section -------------------------------------------------

-- Table com_user

CREATE TABLE com_user(
  usr_ID Number NOT NULL,
  usr_photo Clob NOT NULL,
  usr_name Varchar2(30 ) NOT NULL,
  usr_lastName Varchar2(30 ) NOT NULL,
  usr_surName Varchar2(30 ) NOT NULL,
  usr_identification Varchar2(30 ) NOT NULL,
  usr_email Varchar2(200 ) NOT NULL,
  usr_celphoneNumber Varchar2(30 ) NOT NULL,
  usr_password Varchar2(30 ) NOT NULL,
  usr_tempPassword Varchar2(30 ),
  usr_language Varchar2(2 ) NOT NULL
        CONSTRAINT usr_checkLanguage CHECK (usr_language in ('ES','EN')),
  usr_state Varchar2(1 ) DEFAULT 'I' NOT NULL
        CONSTRAINT usr_checkState CHECK (usr_state in ('A','I')),
  usr_telephone Varchar2(30 ),
  usr_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table com_user

CREATE UNIQUE INDEX usr_identification_index ON com_user (usr_identification)
;

CREATE UNIQUE INDEX usr_email_index ON com_user (usr_email)
;

-- Add keys for table com_user

ALTER TABLE com_user ADD CONSTRAINT PK_com_user PRIMARY KEY (usr_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_user.usr_ID IS 'ID of the user'
;
COMMENT ON COLUMN com_user.usr_photo IS 'photo of the user'
;
COMMENT ON COLUMN com_user.usr_name IS 'name of the user'
;
COMMENT ON COLUMN com_user.usr_lastName IS 'lastname of the user'
;
COMMENT ON COLUMN com_user.usr_surName IS 'surname of the user'
;
COMMENT ON COLUMN com_user.usr_identification IS 'identification of the user'
;
COMMENT ON COLUMN com_user.usr_email IS 'email of the user'
;
COMMENT ON COLUMN com_user.usr_celphoneNumber IS 'celphone number of the user'
;
COMMENT ON COLUMN com_user.usr_password IS 'password of the user'
;
COMMENT ON COLUMN com_user.usr_tempPassword IS 'temporary password of the user'
;
COMMENT ON COLUMN com_user.usr_language IS 'language prefered for the user'
;
COMMENT ON COLUMN com_user.usr_state IS 'state of the user'
;
COMMENT ON COLUMN com_user.usr_telephone IS 'telephone number of the user'
;
COMMENT ON COLUMN com_user.usr_version IS 'register version'
;

-- Table com_chat

CREATE TABLE com_chat(
  cht_ID Number NOT NULL,
  usr_ID_user1 Number NOT NULL,
  usr_ID_user2 Number NOT NULL,
  cht_version Number DEFAULT 1 NOT NULL
)
;

-- Add keys for table com_chat

ALTER TABLE com_chat ADD CONSTRAINT PK_com_chat PRIMARY KEY (cht_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_chat.cht_ID IS 'id of the chat'
;
COMMENT ON COLUMN com_chat.usr_ID_user1 IS 'first user of the chat'
;
COMMENT ON COLUMN com_chat.usr_ID_user2 IS 'second user of the chat'
;
COMMENT ON COLUMN com_chat.cht_version IS 'register version'
;

-- Table com_message

CREATE TABLE com_message(
  msg_ID Number NOT NULL,
  cht_ID Number NOT NULL,
  msg_dateAndHour Timestamp(6) NOT NULL,
  msg_message Varchar2(300 ) NOT NULL,
  usr_ID_sender Number NOT NULL,
  msg_version Number DEFAULT 1 NOT NULL
)
;

-- Add keys for table com_message

ALTER TABLE com_message ADD CONSTRAINT PK_com_message PRIMARY KEY (msg_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_message.msg_ID IS 'id of the message'
;
COMMENT ON COLUMN com_message.cht_ID IS 'id of the chat'
;
COMMENT ON COLUMN com_message.msg_dateAndHour IS 'date of the message'
;
COMMENT ON COLUMN com_message.msg_message IS 'content of the message'
;
COMMENT ON COLUMN com_message.usr_ID_sender IS 'user that sent the message'
;
COMMENT ON COLUMN com_message.msg_version IS 'register version'
;

-- Table com_notificationProcess

CREATE TABLE com_notificationProcess(
  ntp_ID Number NOT NULL,
  ntp_html Varchar2(4000 ) NOT NULL,
  ntp_description Varchar2(300 ),
  ntp_version Number DEFAULT 1 NOT NULL
)
;

-- Add keys for table com_notificationProcess

ALTER TABLE com_notificationProcess ADD CONSTRAINT PK_com_notificationProcess PRIMARY KEY (ntp_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_notificationProcess.ntp_ID IS 'ID of the notification process'
;
COMMENT ON COLUMN com_notificationProcess.ntp_html IS 'html template for the notification process'
;
COMMENT ON COLUMN com_notificationProcess.ntp_description IS 'description of the notification process'
;
COMMENT ON COLUMN com_notificationProcess.ntp_version IS 'register version'
;

-- Table com_constrain

CREATE TABLE com_constrain(
  cnst_ID Number NOT NULL,
  cnst_symbol Varchar2(5 ) NOT NULL,
  cnst_result Varchar2(50 ) NOT NULL,
  var_ID Number NOT NULL,
  cnst_version Number DEFAULT 1 NOT NULL
)
;

-- Add keys for table com_constrain

ALTER TABLE com_constrain ADD CONSTRAINT PK_com_constrain PRIMARY KEY (cnst_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_constrain.cnst_ID IS 'ID of the constrain'
;
COMMENT ON COLUMN com_constrain.cnst_symbol IS 'symbol to be checked'
;
COMMENT ON COLUMN com_constrain.cnst_result IS 'resultable value by symbol'
;
COMMENT ON COLUMN com_constrain.var_ID IS 'varible the constrain belongs to'
;
COMMENT ON COLUMN com_constrain.cnst_version IS 'register version'
;

-- Table com_emails

CREATE TABLE com_emails(
  eml_ID Number NOT NULL,
  eml_emailAddress Varchar2(200 ) NOT NULL,
  eml_sent Varchar2(1 ) NOT NULL
        CONSTRAINT eml_checkSent CHECK (eml_sent in ('Y','N')),
  ntp_ID Number NOT NULL,
  eml_html Varchar2(4000 ) NOT NULL,
  eml_version Number DEFAULT 1 NOT NULL,
  eml_sentDate Date
)
;

-- Add keys for table com_emails

ALTER TABLE com_emails ADD CONSTRAINT PK_com_emails PRIMARY KEY (eml_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_emails.eml_ID IS 'ID of the email'
;
COMMENT ON COLUMN com_emails.eml_emailAddress IS 'email adress to be sent'
;
COMMENT ON COLUMN com_emails.eml_sent IS 'state of the email Y for sended,N for no sended'
;
COMMENT ON COLUMN com_emails.ntp_ID IS 'ID of the notification process this email uses'
;
COMMENT ON COLUMN com_emails.eml_html IS 'html ready to be sent'
;
COMMENT ON COLUMN com_emails.eml_version IS 'register version'
;
COMMENT ON COLUMN com_emails.eml_sentDate IS 'email sent date'
;

-- Table com_emailManager

CREATE TABLE com_emailManager(
  emm_ID Number NOT NULL,
  emm_email Varchar2(100 ) NOT NULL,
  emm_password Varchar2(30 ) NOT NULL,
  emm_limitPerHour Number NOT NULL,
  emm_TimeInMinutes Number NOT NULL,
  emm_version Number DEFAULT 1 NOT NULL,
  emm_port Varchar2(100 ) NOT NULL
)
;

-- Add keys for table com_emailManager

ALTER TABLE com_emailManager ADD CONSTRAINT PK_com_emailManager PRIMARY KEY (emm_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_emailManager.emm_ID IS 'ID of the email manager'
;
COMMENT ON COLUMN com_emailManager.emm_email IS 'sender email'
;
COMMENT ON COLUMN com_emailManager.emm_password IS 'password for the email'
;
COMMENT ON COLUMN com_emailManager.emm_limitPerHour IS 'limit number of emails per hour'
;
COMMENT ON COLUMN com_emailManager.emm_TimeInMinutes IS 'time for the email send timer'
;
COMMENT ON COLUMN com_emailManager.emm_version IS 'register version'
;
COMMENT ON COLUMN com_emailManager.emm_port IS 'port for the email api'
;

-- Table com_variable

CREATE TABLE com_variable(
  var_ID Number NOT NULL,
  var_variable Varchar2(50 ) NOT NULL,
  var_default Varchar2(50 ) NOT NULL,
  var_type Varchar2(1 ) NOT NULL
        CONSTRAINT var_checkType CHECK (var_type in ('S','C','M')),
  ntp_ID Number NOT NULL,
  var_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table com_variable

CREATE UNIQUE INDEX var_variable_index ON com_variable (var_variable)
;

-- Add keys for table com_variable

ALTER TABLE com_variable ADD CONSTRAINT PK_com_variable PRIMARY KEY (var_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_variable.var_ID IS 'ID of the variable'
;
COMMENT ON COLUMN com_variable.var_variable IS 'variable value on the html'
;
COMMENT ON COLUMN com_variable.var_default IS 'default value to replace'
;
COMMENT ON COLUMN com_variable.var_type IS 'type of variable(S=subtitutable,C=Conditional,M=Multimedia)'
;
COMMENT ON COLUMN com_variable.ntp_ID IS 'notification process varaible belongs to'
;
COMMENT ON COLUMN com_variable.var_version IS 'register version'
;

-- Table com_role

CREATE TABLE com_role(
  rol_ID Number NOT NULL,
  rol_name Varchar2(50 ) NOT NULL,
  rol_version Number DEFAULT 1 NOT NULL,
  syst_ID Number NOT NULL
)
;

-- Add keys for table com_role

ALTER TABLE com_role ADD CONSTRAINT PK_com_role PRIMARY KEY (rol_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_role.rol_ID IS 'ID of the role'
;
COMMENT ON COLUMN com_role.rol_name IS 'name of the role'
;
COMMENT ON COLUMN com_role.rol_version IS 'register version'
;

-- Table com_systems

CREATE TABLE com_systems(
  syst_ID Number NOT NULL,
  syst_version Number DEFAULT 1 NOT NULL,
  syst_name Varchar2(50 ) NOT NULL,
  syst_description Varchar2(300 )
)
;

-- Add keys for table com_systems

ALTER TABLE com_systems ADD CONSTRAINT PK_com_systems PRIMARY KEY (syst_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_systems.syst_ID IS 'ID of the system'
;
COMMENT ON COLUMN com_systems.syst_version IS 'register version'
;
COMMENT ON COLUMN com_systems.syst_name IS 'name of the system'
;
COMMENT ON COLUMN com_systems.syst_description IS 'description of the system'
;

-- Table com_rolesystem

CREATE TABLE com_rolesystem(
  sist_ID Number NOT NULL,
  rol_ID Number NOT NULL
)
;

-- Add keys for table com_rolesystem

ALTER TABLE com_rolesystem ADD CONSTRAINT PK_com_rolesystem PRIMARY KEY (sist_ID,rol_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_rolesystem.sist_ID IS 'ID of the system'
;
COMMENT ON COLUMN com_rolesystem.rol_ID IS 'ID of the role'
;

-- Table com_userrole

CREATE TABLE com_userrole(
  usr_ID Number NOT NULL,
  rol_ID Number NOT NULL
)
;

-- Add keys for table com_userrole

ALTER TABLE com_userrole ADD CONSTRAINT PK_com_userrole PRIMARY KEY (usr_ID,rol_ID)
;

-- Table and Columns comments section

COMMENT ON COLUMN com_userrole.usr_ID IS 'ID of the user'
;
COMMENT ON COLUMN com_userrole.rol_ID IS 'ID of the role'
;

-- Table com_file

CREATE TABLE com_file(
  fle_ID Number NOT NULL,
  fle_type Varchar2(10 ) NOT NULL,
  fle_name Varchar2(50 ) NOT NULL,
  fle_content Blob NOT NULL,
  fle_version Number NOT NULL
)
;

-- Add keys for table com_file

ALTER TABLE com_file ADD CONSTRAINT PK_com_file PRIMARY KEY (fle_ID)
;

-- Table com_area

CREATE TABLE com_area(
  are_ID Number NOT NULL,
  are_name Varchar2(50 ) NOT NULL,
  are_state Varchar2(1 ) NOT NULL
        CONSTRAINT are_checkState CHECK (are_state in ('A','I')),
  are_version Number NOT NULL
)
;

-- Add keys for table com_area

ALTER TABLE com_area ADD CONSTRAINT PK_com_area PRIMARY KEY (are_ID)
;

-- Table com_activity

CREATE TABLE com_activity(
  act_ID Number NOT NULL,
  act_indexPerType Number NOT NULL,
  act_name Varchar2(50 ) NOT NULL,
  are_ID Number,
  act_version Number NOT NULL
)
;

-- Add keys for table com_activity

ALTER TABLE com_activity ADD CONSTRAINT PK_com_activity PRIMARY KEY (act_ID)
;

-- Table com_subActivity

CREATE TABLE com_subActivity(
  sAct_ID Number NOT NULL,
  sAct_indexPerType Number NOT NULL,
  sAct_name Varchar2(50 ) NOT NULL,
  act_ID Number,
  sAct_version Number NOT NULL
)
;

-- Add keys for table com_subActivity

ALTER TABLE com_subActivity ADD CONSTRAINT PK_com_subActivity PRIMARY KEY (sAct_ID)
;

-- Table com_management

CREATE TABLE com_management(
  mgt_ID Number NOT NULL,
  mgt_subject Varchar2(100 ) NOT NULL,
  mgt_description Varchar2(300 ) NOT NULL,
  mgt_creationDate Timestamp(6) NOT NULL,
  mgt_maxDateToSolve Timestamp(6) NOT NULL,
  mgt_state Varchar2(20 ) NOT NULL
        CONSTRAINT mgt_checkState CHECK (mgt_state in ('In approval','Rejected','In progress','On hold','Resolved')),
  usr_requesting_ID Number NOT NULL,
  usr_assigned_ID Number NOT NULL,
  act_ID Number,
  mgt_solveDate Timestamp(6),
  mgt_version Number NOT NULL,
  sAct_ID Number
)
;

-- Add keys for table com_management

ALTER TABLE com_management ADD CONSTRAINT PK_com_management PRIMARY KEY (mgt_ID)
;

-- Table com_managementAprobation

CREATE TABLE com_managementAprobation(
  mgtA_ID Number NOT NULL,
  mgtA_state Varchar2(10 ) NOT NULL
        CONSTRAINT mgtA_checkState CHECK (mgtA_state in ('Approved','Rejected','Pending')),
  mgtA_comment Varchar2(200 ),
  mgt_ID Number NOT NULL,
  usr_toAprove_ID Number NOT NULL,
  mgtA_version Number NOT NULL
)
;

-- Add keys for table com_managementAprobation

ALTER TABLE com_managementAprobation ADD CONSTRAINT PK_com_managementAprobation PRIMARY KEY (mgtA_ID)
;

-- Table com_tracing

CREATE TABLE com_tracing(
  tcg_ID Number NOT NULL,
  tcg_creationDate Timestamp(6) NOT NULL,
  tcg_type Varchar2(10 ) NOT NULL
        CONSTRAINT tcg_checkType CHECK (tcg_type in ('text','file')),
  mgt_ID Number NOT NULL,
  usr_ID Number,
  tcg_solutionType Varchar2(10 ) NOT NULL
        CONSTRAINT tcg_checkSolutionType CHECK (tcg_solutionType in ('solved','denied')),
  tcg_solutionDetail Varchar2(200 ),
  tcg_version Number NOT NULL
)
;

-- Add keys for table com_tracing

ALTER TABLE com_tracing ADD CONSTRAINT PK_com_tracing PRIMARY KEY (tcg_ID)
;

-- Table com_filemanagement

CREATE TABLE com_filemanagement(
  mgt_ID Number NOT NULL,
  fle_ID Number NOT NULL
)
;

-- Add keys for table com_filemanagement

ALTER TABLE com_filemanagement ADD CONSTRAINT PK_com_filemanagement PRIMARY KEY (mgt_ID,fle_ID)
;

-- Table com_filetracing

CREATE TABLE com_filetracing(
  tcg_ID Number NOT NULL,
  fle_ID Number NOT NULL
)
;

-- Add keys for table com_filetracing

ALTER TABLE com_filetracing ADD CONSTRAINT PK_com_filetracing PRIMARY KEY (tcg_ID,fle_ID)
;

-- Table com_messagefile

CREATE TABLE com_messagefile(
  msg_ID Number NOT NULL,
  fle_ID Number NOT NULL
)
;

-- Add keys for table com_messagefile

ALTER TABLE com_messagefile ADD CONSTRAINT PK_com_messagefile PRIMARY KEY (msg_ID,fle_ID)
;

-- Table com_fileemail

CREATE TABLE com_fileemail(
  fle_ID Number NOT NULL,
  eml_ID Number NOT NULL
)
;

-- Add keys for table com_fileemail

ALTER TABLE com_fileemail ADD CONSTRAINT PK_com_fileemail PRIMARY KEY (fle_ID,eml_ID)
;

-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE com_chat ADD CONSTRAINT com_user1chat_fk01 FOREIGN KEY (usr_ID_user1) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_chat ADD CONSTRAINT com_user2char_fk02 FOREIGN KEY (usr_ID_user2) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_message ADD CONSTRAINT com_chatmessage_fk01 FOREIGN KEY (cht_ID) REFERENCES com_chat (cht_ID)
;



ALTER TABLE com_emails ADD CONSTRAINT com_notificationProcessemail_fk01 FOREIGN KEY (ntp_ID) REFERENCES com_notificationProcess (ntp_ID)
;



ALTER TABLE com_message ADD CONSTRAINT com_usermessage_fk01 FOREIGN KEY (usr_ID_sender) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_variable ADD CONSTRAINT com_notificationProcessvariable_fk01 FOREIGN KEY (ntp_ID) REFERENCES com_notificationProcess (ntp_ID)
;



ALTER TABLE com_constrain ADD CONSTRAINT com_variableconstrain_fk01 FOREIGN KEY (var_ID) REFERENCES com_variable (var_ID)
;



ALTER TABLE com_rolesystem ADD CONSTRAINT com_rolesystems_fk02 FOREIGN KEY (sist_ID) REFERENCES com_systems (syst_ID)
;



ALTER TABLE com_rolesystem ADD CONSTRAINT com_rolesystems_fk01 FOREIGN KEY (rol_ID) REFERENCES com_role (rol_ID)
;



ALTER TABLE com_userrole ADD CONSTRAINT com_userrole_fk01 FOREIGN KEY (usr_ID) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_userrole ADD CONSTRAINT com_userrole_fk02 FOREIGN KEY (rol_ID) REFERENCES com_role (rol_ID)
;



ALTER TABLE com_role ADD CONSTRAINT com_systemRole_fk01 FOREIGN KEY (syst_ID) REFERENCES com_systems (syst_ID)
;



ALTER TABLE com_activity ADD CONSTRAINT com_areaActivity_fk01 FOREIGN KEY (are_ID) REFERENCES com_area (are_ID)
;



ALTER TABLE com_management ADD CONSTRAINT com_userManagement_fk01 FOREIGN KEY (usr_requesting_ID) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_management ADD CONSTRAINT com_userManagement_fk02 FOREIGN KEY (usr_assigned_ID) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_management ADD CONSTRAINT com_activityManagement_fk01 FOREIGN KEY (act_ID) REFERENCES com_activity (act_ID)
;



ALTER TABLE com_managementAprobation ADD CONSTRAINT com_managementManagementAprobation_fk01 FOREIGN KEY (mgt_ID) REFERENCES com_management (mgt_ID)
;



ALTER TABLE com_managementAprobation ADD CONSTRAINT com_usermanagementAprobation_fk01 FOREIGN KEY (usr_toAprove_ID) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_tracing ADD CONSTRAINT com_managementTracing_fk01 FOREIGN KEY (mgt_ID) REFERENCES com_management (mgt_ID)
;



ALTER TABLE com_tracing ADD CONSTRAINT com_userTracing_fk01 FOREIGN KEY (usr_ID) REFERENCES com_user (usr_ID)
;



ALTER TABLE com_filemanagement ADD CONSTRAINT com_managementFile_fk01 FOREIGN KEY (mgt_ID) REFERENCES com_management (mgt_ID)
;



ALTER TABLE com_filemanagement ADD CONSTRAINT com_managementFile_fk02 FOREIGN KEY (fle_ID) REFERENCES com_file (fle_ID)
;



ALTER TABLE com_filetracing ADD CONSTRAINT com_filetracing_fk02 FOREIGN KEY (tcg_ID) REFERENCES com_tracing (tcg_ID)
;



ALTER TABLE com_filetracing ADD CONSTRAINT com_filetracing_fk01 FOREIGN KEY (fle_ID) REFERENCES com_file (fle_ID)
;



ALTER TABLE com_messagefile ADD CONSTRAINT com_filemessage_fk01 FOREIGN KEY (msg_ID) REFERENCES com_message (msg_ID)
;



ALTER TABLE com_messagefile ADD CONSTRAINT com_filemessage_fk02 FOREIGN KEY (fle_ID) REFERENCES com_file (fle_ID)
;



ALTER TABLE com_fileemail ADD CONSTRAINT com_emailfile_fk02 FOREIGN KEY (fle_ID) REFERENCES com_file (fle_ID)
;



ALTER TABLE com_fileemail ADD CONSTRAINT com_emailfile_fk01 FOREIGN KEY (eml_ID) REFERENCES com_emails (eml_ID)
;



ALTER TABLE com_subActivity ADD CONSTRAINT com_activitySubActivity FOREIGN KEY (act_ID) REFERENCES com_activity (act_ID)
;



ALTER TABLE com_management ADD CONSTRAINT com_subactivityManagement_fk01 FOREIGN KEY (sAct_ID) REFERENCES com_subActivity (sAct_ID)
;





