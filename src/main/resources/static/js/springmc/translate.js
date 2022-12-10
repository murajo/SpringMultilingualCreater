jQuery(function ($) {

  $('#btn-translate').click(function (event) {
    getTargetLang();
    translateProperties();
  });
});

function translateProperties() {
  $.ajax({
    type: "POST",
    cache: false,
    url: 'translate/messageproperties',
    contentType: "application/json",
    data: JSON.stringify(createRequestJson()),
    dataType: 'json',
  }).done(function (data) {
    resetElementResult();
    Object.keys(data).forEach((language) => {
      createElementResult(data[language]);
    })
  }).fail(function (jqXHR, textStatus, errorThrown) {
    console.log(jqXHR.status);
  });
}

function createRequestJson() {
  const data = {};
  data['sourceLang'] = document.getElementById('sourceLang').value;
  data['targetLang'] = getTargetLang();
  data['targetText'] = document.getElementById('targetText').value;
  return data;
}

function getTargetLang() {
  const selects = document.getElementById('targetLang');
  const data = [];
  for (let i = 0; i < selects.length; i++) {
    if (selects[i].selected) {
      data.push(selects[i].value);
    }
  }
  return data;
}

function createElementResult(result) {
  const parentElement = document.getElementById("result");
  const divElement = document.createElement("div");
  const pElement = document.createElement("p");
  const preElement = document.createElement("pre");

  divElement.style = "display: inline-block; padding: 10px; margin-bottom: 10px; border: solid 1px #d6dde4; border-radius: 3px; background-color: rgba(179, 205, 228, 0.667);"
  preElement.style = 'color: green';

  pElement.append(document.createTextNode(result.fileName));
  preElement.append(document.createTextNode(result.text));
  divElement.appendChild(pElement);
  divElement.appendChild(preElement);
  parentElement.appendChild(divElement);
  parentElement.appendChild(document.createElement("br"));
}

function resetElementResult() {
  const resultElement = document.getElementById("result");
  while (resultElement.hasChildNodes()) {
    resultElement.removeChild(resultElement.firstElementChild);
  }
}