program EchoNumberTest;

uses
  EchoNumber;

var
  e: TEcho;
  i: Integer;

begin
  readln(i);
  e := TEcho.Create(i);
  e.Echo;
  writeln(e.FNumberPublic);
  e.Destroy;
end.