-- 회원가입 시 데이터 생성 플로우 --
-- 가입 > 워크스페이스 생성(생략) > 공유스페이스 생성 > OWNER 권한으로 공동작업자에 등록 > 공유스페이스 히스토리 생성
INSERT INTO member (email, password, username, created_at) VALUES ('member1@gmail.com', 'apaqj11!', '멤버1', CURRENT_TIMESTAMP);
INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, path_ids, authorized, sequence, created_at)
VALUES (1, 'https://dummy-url-1.com', 'Dummy Title 1', 'Dummy Description 1', 'https://dummy-profile-image-1.com', 'https://dummy-bg-image-1.com', null, TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (1, 1, CURRENT_TIMESTAMP); -- OWNER 1
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (1, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO member (email, password, username, created_at) VALUES ('member2@gmail.com', 'apaqj11!', '멤버2', CURRENT_TIMESTAMP);
INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, path_ids, authorized, sequence, created_at)
VALUES (2, 'https://dummy-url-2.com', 'Dummy Title 2', 'Dummy Description 2', 'https://dummy-profile-image-2.com', 'https://dummy-bg-image-2.com', null, TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (2, 2, CURRENT_TIMESTAMP); -- OWNER 1
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (2, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO member (email, password, username, created_at) VALUES ('member3@gmail.com', 'apaqj11!', '멤버3', CURRENT_TIMESTAMP);
INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, path_ids, authorized, sequence, created_at)
VALUES (3, 'https://dummy-url-3.com', 'Dummy Title 3', 'Dummy Description 3', 'https://dummy-profile-image-3.com', 'https://dummy-bg-image-3.com', null, TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (3, 3, CURRENT_TIMESTAMP); -- OWNER 1
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (3, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO member (email, password, username, created_at) VALUES ('member4@gmail.com', 'apaqj11!', '멤버4', CURRENT_TIMESTAMP);
INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, path_ids, authorized, sequence, created_at)
VALUES (4, 'https://dummy-url-4.com', 'Dummy Title 4', 'Dummy Description 4', 'https://dummy-profile-image-4.com', 'https://dummy-bg-image-4.com', null, FALSE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (4, 4, CURRENT_TIMESTAMP); -- OWNER 1
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (4, 'OWNER', CURRENT_TIMESTAMP);

-- 임의의 공유스페이스 1에 공동작업자 등록 --
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (2, 1, now()); -- EDITOR 2
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (5, 'EDITOR', now());

INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (3, 1, now()); -- VIEWER 3
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (6, 'VIEWER', now());

INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (4, 1, now()); -- VIEWER 4
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (7, 'VIEWER', now());

-- 임의의 공유스페이스 2에 공동작업자 등록 --
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (1, 2, now()); -- EDITOR 1
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (8, 'EDITOR', now());

INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (3, 2, now()); -- VIEWER 3
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (9, 'VIEWER', now());

INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (4, 2, now()); -- VIEWER 4
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (10, 'VIEWER', now());

-- Template 1 ~ 5
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('회사 채용', '회사 채용 불합격 통지 템플릿 입니다.', '채용, 불합격템플릿, 템플릿', now());
INSERT INTO template_type(template_id, type, created_at) values (1, 'COMPANY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('서비스 소개서', '서비스 소개서를 공유드립니다.', '소개서', now());
INSERT INTO template_type(template_id, type, created_at) values (2, 'COMPANY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('신규 입사자 정보 취합', '신규 입사자용 기본 정보 입력 및 취합을 위한 양식입니다.', '입사자, 정보취합', now());
INSERT INTO template_type(template_id, type, created_at) values (3, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (3, 'SURVEY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('명절 설 취합', '명절 선물 주소지 입력 및 취합을 위한 양식입니다.', '정보취합, 설문, 추천템플릿', now());
INSERT INTO template_type(template_id, type, created_at) values (4, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (4, 'SURVEY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('♥연애계약서♥ - 여자친구Ver', '[개인용 서식]사랑하는 연인들이 서로 지킬 약속에 대해 작성하는 문서입니다.', '연애계약서, 연애, 사랑', now());
INSERT INTO template_type(template_id, type, created_at) values (5, 'PERSONAL', now());

-- Template 6 ~ 10
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('하우스메이트 계약서', '[개인용 서식]하우스메이트에 관한 규칙을 정하는 계약서입니다.', '하우스메이트계약서, 룸메이트계약서', now());
INSERT INTO template_type(template_id, type, created_at) values (6, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('부모님 감사 카드', '[개인용 서식]부모님께 보낼 수 있는 간단한 감사 카드입니다.', '카드, 부모님, 감사', now());
INSERT INTO template_type(template_id, type, created_at) values (7, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('친구 우정 카드', '[개인용 서식]친구에게 보낼 수 있는 간단한 우정 카드입니다.', '카드, 친구, 우정', now());
INSERT INTO template_type(template_id, type, created_at) values (8, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('생일선물 신청서-내 생일선물은 이걸로 부탁해', '[개인용 서식]친구에게 생일 선물로 특정 물건을 받고싶을 때 쓰는 문서입니다.', '생일선물신청서', now());
INSERT INTO template_type(template_id, type, created_at) values (9, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('각서', '[개인용 서식]약속을 지키겠다는 내용을 담은 각서입니다. 증인에게 보내서 서명을 받을 수 있습니다.', '각서, 약속', now());
INSERT INTO template_type(template_id, type, created_at) values (10, 'PERSONAL', now());

-- Template 11 ~ 15
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('강의 요청서', '강의 요청하는 서식입니다.', '개인용서식, 강의, 요청서', now());
INSERT INTO template_type(template_id, type, created_at) values (11, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('협업 제안', '협업을 제안하는 서식입니다.', '개인용서식, 제안서, 협업', now());
INSERT INTO template_type(template_id, type, created_at) values (12, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('개인정보제공동의서', '개인정보제공동의서 양식입니다.', '동의서', now());
INSERT INTO template_type(template_id, type, created_at) values (13, 'PERSONAL', now());
INSERT INTO template_type(template_id, type, created_at) values (13, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (13, 'CONTRACT', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('컨설팅 요청서', '컨설팅을 의뢰하는 서식입니다.', '컨설팅, 요청서', now());
INSERT INTO template_type(template_id, type, created_at) values (14, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (14, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('질문하기', '질문을 해주시면 답변을 드립니다.', 'Q&A, 질의응답, 질문, 답변', now());
INSERT INTO template_type(template_id, type, created_at) values (15, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (15, 'PERSONAL', now());

-- Template 16 ~ 20
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('이력서 및 서류 요청서', '이력서 및 서류를 요청하는 서식입니다.', '요청서', now());
INSERT INTO template_type(template_id, type, created_at) values (16, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (16, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('코칭 요청서', '코칭을 의뢰하는 서식입니다.', '요청서, 코칭', now());
INSERT INTO template_type(template_id, type, created_at) values (17, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (17, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('문의하기', '궁금한 점이나 필요한 사항을 문의 주시면 답변을 드리겠습니다.', '문의', now());
INSERT INTO template_type(template_id, type, created_at) values (18, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (18, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('채용 지원', '채용 지원서를 작성해주세요.', '채용, 채용지원서, 지원서', now());
INSERT INTO template_type(template_id, type, created_at) values (19, 'COMPANY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('양해각서(MOU)', '표준 양해각서입니다.', '협약서, 양해각서', now());
INSERT INTO template_type(template_id, type, created_at) values (20, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (20, 'CONTRACT', now());

-- Template 21 ~ 25
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('Thank-You Letter', '감사한 사람에게 감한 마음을 표현해보세요. 감사한 마음을 표현하면 감사한 일들이 더 많이 생길거에요. :)', '편지, 감사편지', now());
INSERT INTO template_type(template_id, type, created_at) values (21, 'PERSONAL', now());
INSERT INTO template_type(template_id, type, created_at) values (21, 'COMPANY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('카카오 오픈 채팅방 안내', '카카오 오픈 채팅방 참여를 안내하는 단체 공지 템플릿_V1.0', '오픈채팅', now());
INSERT INTO template_type(template_id, type, created_at) values (22, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (22, 'PERSONAL', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('오늘 들은 강의에 대한 의견을 남겨주세요.', '강의에 대한 설문조사 양식입니다.', '설문, 강의리뷰', now());
INSERT INTO template_type(template_id, type, created_at) values (23, 'PERSONAL', now());
INSERT INTO template_type(template_id, type, created_at) values (23, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (23, 'SURVEY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('세미나 신청하기', '세미나 신청 설문 양식입니다.', '설문, 세미나, 신청서', now());
INSERT INTO template_type(template_id, type, created_at) values (24, 'SURVEY', now());
INSERT INTO template_type(template_id, type, created_at) values (24, 'COMPANY', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('커피챗 요청', '커피챗 요청 양식입니다.', '커피챗, 요청서', now());
INSERT INTO template_type(template_id, type, created_at) values (25, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (25, 'PERSONAL', now());

-- Template 26 ~ 30
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('재직증명서', '역요청 기능으로 근로자가 관리자에게 증명서 발급을 요청할 수 있습니다.(전자문서 환경설정에서 역요청 설정)', '재직증명서, 증명서', now());
INSERT INTO template_type(template_id, type, created_at) values (26, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (26, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (26, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('퇴사자 경력증명서', '퇴사한 직원에게 발급하는 경력증명서입니다.', '경력증명서, 증명서, 퇴사자', now());
INSERT INTO template_type(template_id, type, created_at) values (27, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (27, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (27, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('재직자-비밀유지·경업금지서약서', '재직 중인 임직원들을 대상으로 한 비밀유지서약서입니다.', '비밀유지, 비밀유지서약서, 재직자비밀유지서약서, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (28, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (28, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (28, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('퇴사-비밀유지·경업금지서약서', '근로자가 퇴사할 때 기업의 보안사항을 누설하지 않겠다는 내용을 작성하는 서약서입니다.', '비밀유지, 경업금지, 퇴사자비밀유지서약서, 퇴사, 퇴사관련서류', now());
INSERT INTO template_type(template_id, type, created_at) values (29, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (29, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (29, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('개인정보 수집·이용 동의서', '근로자의 개인정보 수집 ·이용 동의서 양식입니다.', '동의서, 개인정보, 개인정보동의서, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (30, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (30, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (30, 'LAW', now());

-- Template 31 ~ 35
INSERT INTO template(title, description, hashtag, created_at)
VALUES ('프리랜서 계약서', '외부직원(프리랜서)과의 업무계약 진행 시 작성하는 문서 양식입니다.', '프리랜서계약서, 프리랜서, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (31, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (31, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (31, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('프리랜서 계약서(첨부파일 요청)', '외부직원(프리랜서)과의 업무계약 진행 시 작성하는 문서 양식입니다. (신분증, 통장사본 요청가능)', '프리랜서계약서, 프리랜서, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (32, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (32, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (32, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('프리랜서 계약서(서약서, 동의서 포함)', '외부직원(프리랜서)과의 업무계약 진행 시 작성하는 문서 양식입니다. (서약서, 동의서 포함서식)', '프리랜서계약서, 프리랜서, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (33, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (33, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (33, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('근로계약서 - 정규직', '근로계약 만료일이 없는 근로계약서입니다. 월급 / 연봉 / 시급 선택이 가능합니다.', '정규직근로계약서, 근로계약서, 정규직, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (34, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (34, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (34, 'LAW', now());

INSERT INTO template(title, description, hashtag, created_at)
VALUES ('근로계약서 - 계약직', '기한 정함이 있는 경우(계약직)의 근로 계약서 양식입니다. 월급 / 연봉 / 시급 선택이 가능합니다.', '계약직근로계약서, 근로계약서, 계약직, 입사', now());
INSERT INTO template_type(template_id, type, created_at) values (35, 'COMPANY', now());
INSERT INTO template_type(template_id, type, created_at) values (35, 'CONTRACT', now());
INSERT INTO template_type(template_id, type, created_at) values (35, 'LAW', now());

-- MyTemplate
INSERT INTO my_template(template_id, member_id, created_at) values (1, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (10, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (11, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (3, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (30, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (25, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (20, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (21, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (33, 1, now());
INSERT INTO my_template(template_id, member_id, created_at) values (27, 1, now());

-- TemplateUsedHistory
INSERT INTO template_used_history(template_id, member_id, used_at) values (1, 1, '2023-09-22T08:34:55.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (2, 1, '2023-09-20T15:21:30.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (5, 1, '2023-09-22T08:30:11.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (6, 1, '2023-09-22T09:31:50.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (7, 1, '2023-09-23T14:27:27.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (21, 2, '2023-09-22T08:34:55.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (15, 2, '2023-09-20T15:21:30.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (6, 2, '2023-09-22T08:30:11.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (2, 2, '2023-09-22T09:31:50.000');
INSERT INTO template_used_history(template_id, member_id, used_at) values (18, 2, '2023-09-23T14:27:27.000');

-- 추가 하위 공유스페이스
INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, parent_space_wall_id, path_ids, authorized, sequence, created_at)
VALUES (1, 'https://dummy-url-1-1.com', 'Dummy Title 1-1', 'Dummy Description 1-1', 'https://dummy-profile-image-1-1.com', 'https://dummy-bg-image-1-1.com', 1, '1', TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (1, 5, CURRENT_TIMESTAMP);
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (11, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, parent_space_wall_id, path_ids, authorized, sequence, created_at)
VALUES (1, 'https://dummy-url-1-2.com', 'Dummy Title 1-2', 'Dummy Description 1-2', 'https://dummy-profile-image-1-2.com', 'https://dummy-bg-image-1-2.com', 1, '1', TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (1, 6, CURRENT_TIMESTAMP);
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (12, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, parent_space_wall_id, path_ids, authorized, sequence, created_at)
VALUES (1, 'https://dummy-url-1-1-1.com', 'Dummy Title 1-1-1', 'Dummy Description 1-1-1', 'https://dummy-profile-image-1-1-1.com', 'https://dummy-bg-image-1-1-1.com', 5, '1-5', TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (1, 7, CURRENT_TIMESTAMP);
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (13, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO space_wall (create_member_id, url, title, description, profile_image_url, background_image_url, parent_space_wall_id, path_ids, authorized, sequence, created_at)
VALUES (2, 'https://dummy-url-2-1.com', 'Dummy Title 2-1', 'Dummy Description 2-1', 'https://dummy-profile-image-2-1.com', 'https://dummy-bg-image-2-1.com', 2, '2', TRUE, 1, CURRENT_TIMESTAMP);
INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (2, 8, CURRENT_TIMESTAMP);
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (14, 'OWNER', CURRENT_TIMESTAMP);

INSERT INTO space_wall_member (member_id, space_wall_id, created_at) VALUES (4, 8, CURRENT_TIMESTAMP);
INSERT INTO space_wall_permission (space_wall_member_id, auths, created_at) VALUES (15, 'VIEWER', CURRENT_TIMESTAMP);

-- Component (parent_space_wall_id = 1) --
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, null, 'text', true, '텍스트 타입 타이틀 입니다.', '텍스트 타입 내용입니다.', 1, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, null, 'line', true, null, null, 2, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, null, 'template', true, null, null, 3, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, 1, null, 'template', true, null, null, 4, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, null, 'page', true, null, null, 5, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, 5, 'page', true, null, null, 6, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, null, 'link', true, '링크 타입 타이틀 입니다.', '링크타입 내용입니다', 7, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (1, null, 6, 'page', true, null, null, 6, now());

-- Component (parent_space_wall_id = 5) --
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (5, null, 7, 'page', true, null, null, 1, now());

-- Component (parent_space_wall_id = 2) --
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, null, 'text', true, '텍스트 타입 타이틀 입니다.', '텍스트 타입 내용입니다.', 1, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, null, 'line', true, null, null, 2, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, null, 'template', true, null, null, 3, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, 2, null, 'template', true, null, null, 4, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, null, 'page', true, null, null, 5, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, 8, 'page', true, null, null, 6, now());
INSERT INTO component (parent_space_wall_id, template_id, this_space_wall_id, type, visible, title, content, sequence, created_at)
VALUES (2, null, null, 'link', true, '링크 타입 타이틀 입니다.', '링크타입 내용입니다', 7, now());

-- SpaceWallTemp
INSERT INTO space_wall_temp (space_wall_id, title, description, profile_image_url, background_image_url, sequence, created_at)
VALUES (1, 'Dummy Title 1', 'Dummy Description 1', 'https://dummy-profile-image-1.com', 'https://dummy-bg-image-1.com', 1, now());
INSERT INTO space_wall_temp (space_wall_id, title, description, profile_image_url, background_image_url, sequence, created_at)
VALUES (2, 'Dummy Title 2', 'Dummy Description 2', 'https://dummy-profile-image-2.com', 'https://dummy-bg-image-2.com', 1, now());
INSERT INTO space_wall_temp (space_wall_id, title, description, profile_image_url, background_image_url, sequence, created_at)
VALUES (3, 'Dummy Title 3', 'Dummy Description 3', 'https://dummy-profile-image-3.com', 'https://dummy-bg-image-3.com', 1, now());
INSERT INTO space_wall_temp (space_wall_id, title, description, profile_image_url, background_image_url, sequence, created_at)
VALUES (5, 'Dummy Title 4', 'Dummy Description 4', 'https://dummy-profile-image-4.com', 'https://dummy-bg-image-4.com', 1, now());

-- ComponentTemp (space_wall_temp_id = 1) --
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, null, 1, 'text', true, '텍스트 타입 타이틀 입니다.', '텍스트 타입 내용입니다.', 1, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, null, 2, 'line', true, null, null, 2, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, null, 3, 'template', true, null, null, 3, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, 1, null, 4, 'template', true, null, null, 4, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, null, 5, 'page', true, null, null, 5, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, 5, 6, 'page', true, null, null, 6, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, null, 7, 'link', true, '링크 타입 타이틀 입니다.', '링크타입 내용입니다', 7, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (1, null, 6, 8, 'page', true, null, null, 6, false, now());

-- ComponentTemp (space_wall_temp_id = 4) --
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (4, null, 7, 9, 'page', true, null, null, 1, false, now());

-- ComponentTemp (space_wall_temp_id = 2) --
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, null, 10, 'text', true, '텍스트 타입 타이틀 입니다.', '텍스트 타입 내용입니다.', 1, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, null, 11, 'line', true, null, null, 2, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, null, 12, 'template', true, null, null, 3, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, 2, null, 13, 'template', true, null, null, 4, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, null, 14, 'page', true, null, null, 5, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, 8, 15, 'page', true, null, null, 6, false, now());
INSERT INTO component_temp (parent_space_wall_temp_id, template_id, this_space_wall_id, component_id, type, visible, title, content, sequence, deleted, created_at)
VALUES (2, null, null, 16, 'link', true, '링크 타입 타이틀 입니다.', '링크타입 내용입니다', 7, false, now());