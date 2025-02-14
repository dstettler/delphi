program EchoNumberTest;

uses
  EchoNumber;

var
  e: TEcho;

begin
  e := TEcho.Create(5);
  e.Echo;
  e.Free;
end.
