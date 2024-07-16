# Scan Viewer 🏥

## 💡프로젝트 개요
Scan Viewer는 의료 분야에서 빠르고 정확한 진단과 의료진이 다양한 의료 영상을 빠르고 정확하게 판독할 수 있고 이를 효과적으로 처리하고 뷰잉 할 수 있는 웹 프로젝트입니다.

## 🐼 팀원 소개
- 팀장 : 전누리 [@nooleee](https://github.com/nooleee)
- 서기 : 김민기 [@mingikim-giv](https://github.com/mingikim-giv)
- 팀원 : 김경현 [@k0gang](https://github.com/k0gang)
- 팀원 : 박인혁 [@ryuuki98](https://github.com/ryuuki98)

## 🗒 작업 규칙
- 질문 및 건의사항은 슬랙을 적극 사용해주세요 ! 👍
- 작업 branch는 feature/[작업내용] 형태로 올려주세요.
- Commit Message는 최대한 자세히 적어주세요.
- develop에 직접 올리지 마세요 !

## ⏲️ 개발 기간
- 2024.06.18 ~ 2024.07.16

## ⭐ 주요 기능
### 🔒로그인 / 회원가입
- 로그인(아이디, 비밀번호) 입력 후 Scan Viewer 홈페이지 접속 가능
- 회원가입 시(아이디, 비밀번호, 이름, 휴대전화, 생년월일, 그룹) 필수 정보 작성 후 회원 가입

### 🖼 워크 리스트
- 워크 리스트(환자 번호, 환자 이름, 검사 장비, 날짜, 판독 결과 조건을 선택, 추가하여 검색)
- 워크 리스트 정렬(내림차순 정렬)
- 해당 스터디 선택 시 워크 리스트에서도 리포트 생성 및 수정 가능

### 🔍 DICOM 뷰어
- 뷰어 랜더링(검사를 선택하면 해당 스터디키와 동일한 시리즈 키를 가진 이미지 리스트를 불러와 뷰포트에 랜더링)
- 뷰어 툴바(cornerstone tools 기능을 이용하여 줌 인/아웃, 회전, 팬, 스택, ... 사용 가능)
  
### 📄 리포트
- 리포트(환자 정보, 의사 소견, 결론, 판독 결과, 질병 코드) 필수 정보 작성 후 생성 및 수정
- 리포트 생성 전 동의서 작성 후 리포트 생성 가능
- 질병 코드 검색

### 💬 채팅
- 유저 간 채팅
- 실시간 업데이트

## 🛠 기술 스택
### Front-End

![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

### Back-End

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

### Database

![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

### Environment

![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

### Communication

![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![KakaoTalk](https://img.shields.io/badge/kakaotalk-ffcd00.svg?style=for-the-badge&logo=kakaotalk&logoColor=000000)

## 🎥 데모 영상
### 로그인
<img src = "https://github.com/user-attachments/assets/09ade18e-6da0-462c-beee-96260147d301" width = "650px" height = "500px">

### 워크 리스트
- 워크 리스트 검색

<img src = "https://github.com/user-attachments/assets/3128245d-c6de-4f94-9cef-fdba2f1c364f" width = "650px" height = "500px">

- 워크 리스트 상세 검색

<img src = "https://github.com/user-attachments/assets/6e59c4dd-aeed-458e-853b-747beadae0c4" width = "650px" height = "500px">

### DICOM 뷰어
- DICOM 뷰어 썸네일 및 스크롤

<img src = "https://github.com/user-attachments/assets/57a5a68e-bc45-43d8-85e7-7006046009e9" width = "650px" height = "500px">

- DICOM 뷰어 툴 기능

<img src = "https://github.com/user-attachments/assets/308c14e7-4775-4b94-8328-fec8cf98bec3" width = "650px" height = "500px">

### 리포트
- 리포트 생성

<img src = "https://github.com/user-attachments/assets/1e3376ee-b25e-4ff3-b89a-2c81e73f8515" width = "650px" height = "500px">

- 워크 리스트에서 리포트 생성 확인

<img src = "https://github.com/user-attachments/assets/8241a770-f2d1-4f60-bb42-749605e64a86" width = "650px" height = "500px">

### 채팅
<img src = "https://github.com/user-attachments/assets/dee206c3-009a-41db-9217-d0cc5ecc43a8" width = "650px" height = "500px">

## 📁 관련 서류
- [요구사항 정의서](https://docs.google.com/spreadsheets/d/1-tI0E_GarMRn_contiOOuk-Shl9YqmpH/edit?usp=drive_link&ouid=110736728495168816564&rtpof=true&sd=true)
- [테이블 정의서](https://docs.google.com/spreadsheets/d/1BDz4Bfe5xsuNWAxetQn96UAuJGjRhTyI/edit?usp=drive_link&ouid=110736728495168816564&rtpof=true&sd=true)
- [인터페이스 정의서](https://docs.google.com/spreadsheets/d/1MDwN-9_7vn8hEllSrnn9dPeUj0312a7o/edit?usp=drive_link&ouid=110736728495168816564&rtpof=true&sd=true)
- [화면 설계서](https://drive.google.com/file/d/1o-ojLnxjBB7aKxcs89b6o_zteElL-t-3/view?usp=drive_link)
- [데이터베이스 ERD](https://drive.google.com/file/d/1ayQx9zvwuRL_RX7tUKgem98d8e9VQdgl/view?usp=drive_link)

