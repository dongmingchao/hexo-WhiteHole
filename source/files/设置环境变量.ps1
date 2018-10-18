$vars = "D:\Program Files\nodejs","D:\Software\VSCode\bin","D:\Software\Git\bin","D:\Program Files\Python\3.7.0",$(npm prefix -g)
$path = [environment]::GetEnvironmentvariable("Path", "Machine")
$paths = $path.Split(';')
$varsWill = ""
for ($i = 0; $i -lt $vars.Count; $i++) {
    if ($paths.Contains($vars[$i])) {
        # Write-Output $i
    } else {
        $varsWill += $vars[$i]+";"
    }
}
Write-Output "will be add"$varsWill
Write-Output "old env"$path
[environment]::SetEnvironmentVariable("Path", $path+$varsWill, "Machine")