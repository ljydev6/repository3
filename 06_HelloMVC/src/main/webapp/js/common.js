/**
 * 
 */
function maketable(data){
	const $table = $('<table>').html('<thead><tr><th>아이디</th>'+
						'<th>이름</th><th>나이</th><th>성별</th><th>이메일</th><th>주소</th>'+
						'<th>전화번호</th><th>취미</th><th>등록일</th></tr></thead>');
						const $tbody = $('<tbody>');
						data.forEach(e=>{
							const $tr = $('<tr>');
							const $userid = $('<td>').text(e.userid);
							const $username = $('<td>').text(e.username);
							const $age = $('<td>').text(e.age);
							const $email = $('<td>').text(e.email);
							const $address = $('<td>').text(e.address);
							const $phone = $('<td>').text(e.phone);
							const $gender = $('<td>').text(e.gender);
							/* const $tdhobby = $('<td>');
							const $ulhobby = $('<ul>');
							if(e.hobby!=null){
							e.hobby.forEach(h=>{
								$ulhobby.append($('<li>').text(h))
							});
							$tdhobby.append($ulhobby);
							} */
							const $tdhobby = $('<td>').text(e.hobby);
							const $enrolldate = $('<td>').text(e.enrolldate);
							$tr.append($userid).append($username).append($age).append($gender)
								.append($email).append($address).append($phone).append($tdhobby)
								.append($enrolldate);
							$tbody.append($tr);
						});
						$table.append($tbody);
						return $table;
}