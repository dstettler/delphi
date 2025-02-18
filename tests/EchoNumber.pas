unit EchoNumber;

interface

type
  TEcho = class
  private
    FNumber: Integer;
  public
    constructor Create(ANumber: Integer);
    destructor Destroy; override; // Destructor
    function Echo: Integer; // Method to return the number and print it
  public
    FNumberPublic: Integer;
  end;

implementation

function NumberDoubler(ToBeDoubled: Integer): Integer;
begin
  Result := ToBeDoubled * 2;
end;

constructor TEcho.Create(ANumber: Integer);
begin
  FNumber := ANumber;
  FNumberPublic := NumberDoubler(ANumber);
end;

destructor TEcho.Destroy;
begin
  // Any necessary cleanup code can go here.
  writeln('Ran the destructor!');
  inherited Destroy;
end;

function TEcho.Echo: Integer;
begin
  writeln('Echoed Number: ', FNumber);
  Result := FNumber;  // Return the number that is echoed
end;

end.
