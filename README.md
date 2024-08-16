## 구현 기능 및 API 설계표
| 기능           | method | URL                      | request        | return value                |
|----------------|--------|--------------------------|----------------|-----------------------------|
| 일정등록       | post   | /api/schedule             | RequestBody    | 등록된 책 정보              |
| 일정 선택조회  | get    | /api/schedule/{schedule_id} | PathVariable   | 선택된 책 정보              |
| 일정 목록조회  | get    | /api/schedule             | RequestParam   | 등록된 모든 책 정보         |
| 일정 수정      | put    | /api/schedule/{schedule_id} | PathVariable, RequestBody | 수정된 새로운 책 정보       |
| 일정 삭제      | delete | /api/schedule/{schedule_id} | PathVariable, RequestParam | -                           |


## ERA

![Alt text](img/era.png)

