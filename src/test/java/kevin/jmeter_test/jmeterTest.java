package kevin.jmeter_test;

/**
 * Jmeter 소개
 * 성능 측정 및 부하(load)테스트 기능을 제공하는 오픈 소스
 *
 * jmeter 다운로드 완료 (zip)
 * 실행파일경로 /bin/jmeter.sh
 *
 * jmeter 실행
 * jmeter를 돌리는 서버와  테스트하려는 서버가 달라야함. (로컬에서 서버구동하고 로컬에서 jmeter 켜서 테스트하면안됨..)
 * 1. 테스트 계획 > 추가 > 쓰레드 그룹 추가
 * 2. 쓰레드 그룹 설정 - 쓰레드수(사용자수) / 쓰레드 생성시간(초) / 루프(반복횟수) 설정
 * 3. 쓰레드 그룹 우클릭 > 추가 > 표본추출기 > http 요청 추가 > http 요청 설정 (url 등)
 * 4. 쓰레드 그룹 우클릭 > 추가 > 리스너 > 결과들의 트리보기, 요약보고서, 결과들을 테이블로 보기 일단 선택해봄.
 * 5. 녹색 재생버튼 클릭 시 api 요청 실행
 * 6. post url 요청 시 415 에러발생 > http요청 > 추가 > 설정엘리먼트 > http 헤더관리자
 * 7. content-type / application/json 추가해줌.
 * 8. 여러 보고서 리스너를 통해 결과 확인
 * 9. Assertion을 통해 응답데이터의 유효성을 검증 할 수도 있음.
 * 10.
 *
 * 대체 프로그램
 * Gatling
 * nGrinder (네이버에서 제작)
 */
public class jmeterTest {

}
