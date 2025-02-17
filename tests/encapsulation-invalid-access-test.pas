program EchoNumberTest;

uses
  EchoNumber;

var
  e: TEcho;
  i: Integer;

begin
  i := 5;
  e := TEcho.Create(i);
  e.Echo;
  writeln(e.FNumber);
  e.Destroy;
end.