var IMP = window.IMP; // 생략 가능
IMP.init("imp07007375"); // 예: imp00000000

// const [id, pw, pw2, phone, email] = document.getElementsByTagName('input');
const [id, pw, pw2, phone, email] = document.querySelectorAll('label > input')
const [idCheck, pwCheck, pw2Check, phoneCheck, emailCheck] = document.getElementsByClassName('checkBox')
const joinBtn = document.querySelector('#joinBtn');
const certBtn = document.querySelector('#cert-btn');
const csrfToken = document.getElementsByName('_csrf')[0].value;
const ciInput = document.querySelector('.ci-input');

let moved = false;
const trueCheck = '<i style="color: limegreen" class="fa-solid fa-check"></i>';
const falseCheck = '<i style="color: orangered" class="fa-solid fa-exclamation"></i>';
// const telDiv = document.querySelector('.tel-div');

// 항목별 정규식
// id : 글자수 6 ~ 12, 특수문자 x, 영어 숫자만 가능
const idRegExp = /^[a-z]+[a-z0-9]{5,11}$/g;
// pw : 글자수 8 ~ 16, 특수문자 1개 이상 영어 숫자 무조건 포함
const pwRegExp = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
// pw2 : 얘는 pw에서 검사하고 같은지 확인만 하면 되겠지?................
// name : 꼭 필요하나? 어차피 본인인증이 있을텐데 한글만 입력가능
const nameRegExp = /^[ㄱ-ㅎ가-힣]+$/;
// phone : 01로 시작 중간 3 ~ 4자 마지막 4자
const phoneRegExp = /^01([0|1|[6-9])-?([0-9]{3,4})-?([0-9]{4})$/;
const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

//정규식 확인

id.onchange = () => {
    if (idRegExp.test(id.value)) {
        console.log(id.value)
        idCheck.innerHTML = trueCheck; //맞을때 반응
    } else {
        idCheck.innerHTML = falseCheck; // 아닐때 반응
    }
}

pw.onchange = () => {
    if (pwRegExp.test(pw.value)) {
        pwCheck.innerHTML = trueCheck;
    } else {
        pwCheck.innerHTML = falseCheck;
    }
    if (pw.value === pw2.value) {
        pw2Check.innerHTML = trueCheck;
    } else {
        pw2Check.innerHTML = falseCheck;
    }
}

pw2.onchange = () => {

    if (pw.value === pw2.value && pwRegExp.test(pw2.value)) {
        pw2Check.innerHTML = trueCheck;
    } else {
        pw2Check.innerHTML = falseCheck;
    }
}


email.onchange =() => {
    if (emailRegExp.test(email.value)) {
        emailCheck.innerHTML = trueCheck;
    } else {
        emailCheck.innerHTML = falseCheck;
    }
}


phone.onchange = () => {
    console.log(phone.value.length)
    phone.value = phone.value.replace(/[^0-9]/g, '').replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    if (phoneRegExp.test(phone.value)) {
        phoneCheck.innerHTML = trueCheck;
    } else {
        phoneCheck.innerHTML = falseCheck;
    }
}


btn.onmouseenter = () => {
    console.log(idRegExp.test(id.value));
    if (idRegExp.test(id.value) && pwRegExp.test(pw.value) && pw.value === pw2.value && phoneRegExp.test(phone.value) && emailRegExp.test(email.value)) {
    } else {
        move_btn();
    }
};


function move_btn() {
    if (moved) {
        btn.style.left = '0';
        moved = false;
    } else {
        btn.style.left = '100px';
        moved = true;
    }
}

certBtn.onclick = () => {
    console.log('클릭')
    user_certification();
}




// 유저 본인인증
function user_certification(){
    IMP.certification({
        merchant_uid: "1"
    }, function (rsp) { // callback
        if (rsp.success) {
            console.log(rsp);
            certBtn.style.display='none';
            get_certification_unique_key(rsp.imp_uid)
        } else {

        }
    });
}

function get_certification_unique_key(imp_uid){
    fetch('/user/cert/token')
        .then(resp => resp.text())
        .then(value => {
            console.log("token: ", value)
            fetch(`/user/cert/${imp_uid}`,{
                method: "POST",
                headers: {
                    'X-Csrf-Token': csrfToken,
                    'Content-Type': "application/json"
                },
                body: value,
            })
                .then(resp => resp.text())
                .then(value => {
                    console.log('성공:', value)
                    ciInput.value = value;
                });
        });
}


joinBtn.onclick = () => {

}






