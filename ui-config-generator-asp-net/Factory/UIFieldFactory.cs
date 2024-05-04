using System.Reflection;
using UIConfigGenerator.Attributes;
using UIConfigGenerator.Config;

namespace UIConfigGenerator.Parser
{
    internal static class UIFieldFactory
    {
        public static UIField CreateField(object fieldInfo, Type fieldType, string displayName, string? codeName)
        {
            return CreateField(fieldInfo, fieldType, displayName, codeName, []);
        }

        private static UIField CreateField(object? fieldInfo, Type fieldType, string? displayName, string? codeName, HashSet<Type> parsedTypesSet)
        {
            if (parsedTypesSet.Contains(fieldType))
            {
                throw new ArgumentException("Circular element link: " + fieldType);
            }
            bool isRequired = true;
            Type? nullable = Nullable.GetUnderlyingType(fieldType);
            if (nullable != null)
            {
                isRequired = false;
                fieldType = nullable;
            }
            if (IsNumericType(fieldType))
            {
                int? min = null, max = null;
                if (fieldInfo != null)
                {
                    NumberFieldAttribute? attribute = GetFieldAttribure(fieldInfo, typeof(NumberFieldAttribute)) as NumberFieldAttribute;
                    min = attribute?.Min;
                    max = attribute?.Max;
                }
                return new UINumberField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.NUMBER, Required = isRequired, Min = min, Max = max };
            }
            else if (fieldType == typeof(string))
            {
                int? minLength = null, maxLength = null;
                string? pattern = null;
                if (fieldInfo != null)
                {
                    TextFieldAttribute? attribute = GetFieldAttribure(fieldInfo, typeof(TextFieldAttribute)) as TextFieldAttribute;
                    minLength = attribute?.MinLength;
                    maxLength = attribute?.MaxLength;
                    pattern = attribute?.Pattern;
                }
                return new UITextField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.TEXT, Required = isRequired, 
                    MinLength = minLength, MaxLength = maxLength, Pattern = pattern};
            }
            else if (fieldType == typeof(bool))
            {
                return new UIField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.BOOL, Required = isRequired };
            }
            else if (fieldType.IsEnum)
            {
                var values = fieldType.GetMembers(BindingFlags.Public | BindingFlags.Static);
                var dict = new Dictionary<string, string>();
                foreach (var value in values)
                {
                    dict.Add(value.Name, GetDisplayName(value, value.Name));
                }
                return new UIEnumField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.ENUM, Required = isRequired, SubmitToDisplayValues = dict };
            }
            else if (fieldType.IsArray)
            {
                UIField element = CreateField(null, fieldType.GetElementType(), null, null, parsedTypesSet);
                return new UIListField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.LIST, Required = isRequired, Element = element };
            }
            else
            {
                var enumerable = fieldType.GetInterfaces().Where(t => t.IsGenericType && t.GetGenericTypeDefinition() == typeof(IEnumerable<>)).FirstOrDefault();
                if (enumerable != null)
                {
                    Type itemType = enumerable.GenericTypeArguments[0];
                    UIField element = CreateField(null, itemType, null, null, parsedTypesSet);
                    return new UIListField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.LIST, Required = isRequired, Element = element };
                }
                else
                {
                    List<UIField> uiInnerFields = [];
                    parsedTypesSet.Add(fieldType);
                    foreach (var innerField in fieldType.GetProperties())
                    {
                        string innerName = GetDisplayName(innerField, innerField.Name);
                        string innerCodeName = SanitizeCodeName(innerField.Name);
                        var uiInnerField = CreateField(innerField, innerField.PropertyType, innerName, innerCodeName, parsedTypesSet);
                        uiInnerFields.Add(uiInnerField);
                    }
                    return new UIClassField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.CLASS, Required = isRequired, InnerFields = uiInnerFields };
                }
            }
        }

        public static bool IsNumericType(Type type)
        {
            if (type.IsEnum) return false;
            switch (Type.GetTypeCode(type))
            {
                case TypeCode.Byte:
                case TypeCode.SByte:
                case TypeCode.UInt16:
                case TypeCode.UInt32:
                case TypeCode.UInt64:
                case TypeCode.Int16:
                case TypeCode.Int32:
                case TypeCode.Int64:
                case TypeCode.Decimal:
                case TypeCode.Double:
                case TypeCode.Single:
                    return true;
                default:
                    return false;
            }
        }

        public static string GetDisplayName(MemberInfo field, string codeName)
        {
            var displayNameAttr = Attribute.GetCustomAttribute(field, typeof(DisplayNameAttribute)) as DisplayNameAttribute;
            return GetDisplayName(displayNameAttr, codeName);
        }

        public static string GetDisplayName(ParameterInfo field, string codeName)
        {
            var displayNameAttr = Attribute.GetCustomAttribute(field, typeof(DisplayNameAttribute)) as DisplayNameAttribute;
            return GetDisplayName(displayNameAttr, codeName);
        }

        private static string GetDisplayName(DisplayNameAttribute? displayNameAttr, string codeName)
        {
            return displayNameAttr != null ? displayNameAttr.Name : codeName;
        }

        private static Attribute? GetFieldAttribure(object fieldInfo, Type attributeType) 
        {
            if (typeof(MemberInfo).IsAssignableFrom(fieldInfo.GetType()))
            {
                return Attribute.GetCustomAttribute((MemberInfo)fieldInfo, attributeType);
            }
            else if (typeof(ParameterInfo).IsAssignableFrom(fieldInfo.GetType()))
            {
                return Attribute.GetCustomAttribute((ParameterInfo)fieldInfo, attributeType);
            }
            return null;
        }

        public static string SanitizeCodeName(string codeName)
        {
            return char.ToLower(codeName[0]) + codeName[1..];
        }
    }
}
