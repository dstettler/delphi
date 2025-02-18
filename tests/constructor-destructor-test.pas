program EchoNumberTest;

uses
  EchoNumber;

var
  e: TEcho;
  i: Integer;
  writtenString: String;

begin
  writeln('Please enter an Integer.');
  readln(i);
  e := TEcho.Create(i);
  e.Echo;
  writeln(e.FNumberPublic);
  writeln('FNumberPublic is doubled from the int you entered. Enter a new value for it.');
  readln(e.FNumberPublic);
  writeln('Modified public field to: ', e.FNumberPublic);
  writeln('Enter any string');
  readln(writtenString);
  writeln('You wrote: "', writtenString, '"');
  e.Destroy;
end.