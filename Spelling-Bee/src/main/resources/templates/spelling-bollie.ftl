<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spelling Bollie</title>
</head>
<body>

<div>${gameId}</div>
<div>${compulsoryLetter}</div>
<#list consonantSelection as consonant>
    <div>${consonant}</div>
</#list>

<#list vowelSelection as vowel>
    <div>${vowel}</div>
</#list>


<#list foundWords as foundWord>
    <div>${foundWord}</div>
</#list>


</body>
</html>
