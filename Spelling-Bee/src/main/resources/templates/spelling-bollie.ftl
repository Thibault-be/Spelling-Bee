<!DOCTYPE html>
<html lang = "en">
<head>
    <meta charset="UTF-8">
    <title>Spelling Bollie</title>
</head>
<body>

<div>gameId: ${gameId}</div>
<br>
<div>compulsory: ${compulsoryLetter}</div>

<br>
<div> consonants </div>
<#list consonantSelection as consonant>
    <div>${consonant}</div>
</#list>
<br>
<div> vowels </div>
<#list vowelSelection as vowel>
    <div>${vowel}</div>
</#list>

<br>
<div>score</div>
<div>${score}</div>

<br>
<ul>
<#list possibleWords as word>
    <li>${word}</li>
</#list>
</ul>


<br>
<#if lastGuess??>
    <div>last guess</div>
    <div>${lastGuess}</div>
</#if>


<br>
<div> found words </div>
<#list foundWords as foundWord>
    <div>${foundWord}</div>
</#list>


</body>
</html>
